package patternvisor;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ProjectContext context = new ProjectContext("src/test/resources/symbolSolverTest");
//        SingletonDetector singletonDetector = new SingletonDetector();
//        singletonDetector.findPatterns(context);

        context.getAllClasses().values().forEach(c -> {
                System.out.println("klasa: " + c.getNameAsString());
                System.out.println(c.resolve().asReferenceType().getName());
                }
        );

    }
}