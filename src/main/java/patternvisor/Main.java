package patternvisor;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ProjectContext context = new ProjectContext("src/test/resources");
        SingletonDetector singletonDetector = new SingletonDetector();
        AnalysisResult rezultat = singletonDetector.findPatterns(context);
        rezultat.printFindings();

    }
}