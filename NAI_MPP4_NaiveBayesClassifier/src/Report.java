import java.math.BigDecimal;
import java.math.MathContext;

public class Report {

    private BigDecimal truePositive = BigDecimal.ZERO;
    private BigDecimal falsePositive = BigDecimal.ZERO;
    private BigDecimal trueNegative = BigDecimal.ZERO;
    private BigDecimal falseNegative = BigDecimal.ZERO;
    private BigDecimal acc = BigDecimal.ZERO;
    private BigDecimal all = BigDecimal.ZERO;

    public BigDecimal getTruePositive() {
        return truePositive;
    }

    public void setTruePositive(BigDecimal truePositive) {
        this.truePositive = truePositive;
    }

    public BigDecimal getFalsePositive() {
        return falsePositive;
    }

    public void setFalsePositive(BigDecimal falsePositive) {
        this.falsePositive = falsePositive;
    }

    public BigDecimal getTrueNegative() {
        return trueNegative;
    }

    public void setTrueNegative(BigDecimal trueNegative) {
        this.trueNegative = trueNegative;
    }

    public BigDecimal getFalseNegative() {
        return falseNegative;
    }

    public void setFalseNegative(BigDecimal falseNegative) {
        this.falseNegative = falseNegative;
    }

    public BigDecimal getAcc() {
        return acc;
    }

    public void setAcc(BigDecimal acc) {
        this.acc = acc;
    }

    public BigDecimal getAll() {
        return all;
    }

    public void setAll(BigDecimal all) {
        this.all = all;
    }

    public void printReport(){
        System.out.println("Dokładność: "+acc.divide(all, MathContext.DECIMAL128));

        BigDecimal precyzja = truePositive.divide(truePositive.add(falsePositive), MathContext.DECIMAL128);
        BigDecimal pelnosc = truePositive.divide(truePositive.add(falseNegative), MathContext.DECIMAL128);

        BigDecimal sum = precyzja.add(pelnosc);

        System.out.println("Precyzja: "+precyzja);
        System.out.println("Pełność: "+pelnosc);
        System.out.println("F-miara: "+precyzja.multiply(pelnosc).multiply(BigDecimal.valueOf(2.0)).divide(sum, MathContext.DECIMAL128));

        System.out.println();
        System.out.println("[Zaklasyfikowano jako] ->\t\tspam\tniespam");
        System.out.printf("%35s","spam\t\t"+truePositive+"\t\t"+falseNegative+"\n");
        System.out.printf("%34s","niespam\t\t"+falsePositive+"\t\t"+trueNegative);
        System.out.println();
    }
}
