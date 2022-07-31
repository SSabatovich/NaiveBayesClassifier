import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Classifier {

    final private List<String> types;
    final private List<String[]> trainVecs;

    public Classifier(List<String[]> trainData) {
        this.trainVecs = trainData;
        this.types = getAllTypes();
    }
    private List<String> getAllTypes(){
        return trainVecs.stream().map(e->e[e.length-1]).distinct().collect(Collectors.toList());
    }

    public void test(List<String[]> testData){

        Report report = new Report();

        for (String[] vec: testData) {

            String[] vecToTest = new String[vec.length-1];

            System.arraycopy(vec, 0, vecToTest, 0, vec.length - 1);

            Map<String, BigDecimal> prawdopodobienstwoTypu = new HashMap<>();

            for (String mozliwyTyp: types) {
                BigDecimal prawdopodobienstwo = getPrawdopodobienstwo(mozliwyTyp);

                for (int i = 0; i < vecToTest.length; i++) {
                    prawdopodobienstwo = prawdopodobienstwo.multiply(getPrawdopodobienstwo(i,vecToTest[i], mozliwyTyp));
                }
                prawdopodobienstwoTypu.put(mozliwyTyp, prawdopodobienstwo);
            }

            String res = prawdopodobienstwoTypu.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow(IllegalStateException::new).getKey();


            String exp = vec[vec.length-1];

            if (res.equals(exp)) {
                report.setAcc(report.getAcc().add(BigDecimal.ONE));
            }

            //licze precyzje i pelnosc
            if(!res.equals(exp)){
                if(exp.equals(types.get(0)))
                    report.setFalseNegative(report.getFalseNegative().add(BigDecimal.ONE));
                else
                    report.setFalsePositive(report.getFalsePositive().add(BigDecimal.ONE));
            }
            else{
                if(exp.equals(types.get(0)))
                    report.setTruePositive(report.getTruePositive().add(BigDecimal.ONE));
                else
                    report.setTrueNegative(report.getTrueNegative().add(BigDecimal.ONE));
            }
        }

        report.setAll(BigDecimal.valueOf(testData.size()));
        report.printReport();
    }

    private BigDecimal getPrawdopodobienstwo(int columnIndex, String type, String conditionType){

        BigDecimal licznik = BigDecimal.valueOf(trainVecs.stream().filter(e->e[e.length-1].equals(conditionType)).map(e->e[columnIndex]).filter(e->e.equals(type)).count());

        BigDecimal mianownik =  BigDecimal.valueOf(trainVecs.stream().filter(e->e[e.length-1].equals(conditionType)).count());

        //gladzenie danych, nie moge miec tam 0
        if(licznik.equals(BigDecimal.ZERO)){
            mianownik=mianownik.add(BigDecimal.valueOf(getAllTypes().size()));
            licznik = licznik.add(BigDecimal.ONE);
        }

        return licznik.divide(mianownik, MathContext.DECIMAL128);
    }

    private BigDecimal getPrawdopodobienstwo(String type){
        return BigDecimal.valueOf(trainVecs.stream().map(e->e[e.length-1]).filter(e->e.equals(type)).count()).divide(BigDecimal.valueOf(trainVecs.size()), MathContext.DECIMAL128);
    }
}