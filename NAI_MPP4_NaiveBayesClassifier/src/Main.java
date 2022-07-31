import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        final String testDataPath = "data/test_data.csv";
        final String trainDataPath = "data/train_data.csv";
        final String wordListPath = "data/word_list.txt";

        try {
            List<String> words = Files.readAllLines(Paths.get(wordListPath));
            String[] wordsArr = words.toArray(new String[0]);

            List<String[]> testData = read(testDataPath);

            List<String[]> trainData = read(trainDataPath);

            Classifier classifier = new Classifier(trainData);
            classifier.test(testData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> read(String path){
        List<String[]> vecs = new LinkedList<>();
        try{
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if(!line.trim().isEmpty()) {
                    vecs.add(line.trim().replace(" ", "").split(","));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return vecs;
    }
}