package patternvisor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SingletonDetector implements PatternDetector {
    @Override
    public AnalysisResult findPatterns(ProjectContext context) {
        List<PatternFinding> findings = new ArrayList<>();

        for (ClassOrInterfaceDeclaration analyzedClass : context.getAllClasses().values()) {

            List<String> evidence = new ArrayList<>();
            if (hasOnlyPrivateConstructors(analyzedClass)) {
                evidence.add("All explicitly declared constructors are private.");
            }
            if (hasPrivateStaticSelfField(analyzedClass)) {
                evidence.add("A private static field has the same type as this class.");
            }
            if (hasPublicStaticSelfFactory(analyzedClass)) {
                evidence.add("A public static method returns this class.");
            }

            if (evidence.size() == 3) {
                findings.add(new PatternFinding(
                        getPatternName(),
                        UtilityFunctions.getQualifiedName(analyzedClass),
                        getSourceFile(analyzedClass),
                        getStartLine(analyzedClass),
                        evidence
                ));
            }
        }

        return new AnalysisResult(findings);
    }

    private boolean hasOnlyPrivateConstructors(ClassOrInterfaceDeclaration analyzedClass) {
        List<ConstructorDeclaration> constructors = analyzedClass.getConstructors();
        return !constructors.isEmpty() && constructors.stream().allMatch(ConstructorDeclaration::isPrivate);
    }

    private boolean hasPrivateStaticSelfField(ClassOrInterfaceDeclaration analyzedClass) {
        for (FieldDeclaration field : analyzedClass.findAll(FieldDeclaration.class)) {
            if (!field.isStatic() || !field.isPrivate()) {
                continue;
            }

            for (VariableDeclarator variable : field.getVariables()) {
                if (variable.getTypeAsString().equals(analyzedClass.getNameAsString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPublicStaticSelfFactory(ClassOrInterfaceDeclaration analyzedClass) {
        for (MethodDeclaration method : analyzedClass.getMethods()) {
            if (method.isPublic()
                    && method.isStatic()
                    && method.getTypeAsString().equals(analyzedClass.getNameAsString())) {
                return true;
            }
        }
        return false;
    }

    private Path getSourceFile(ClassOrInterfaceDeclaration analyzedClass) {
        return analyzedClass.findCompilationUnit()
                .flatMap(CompilationUnit::getStorage)
                .map(CompilationUnit.Storage::getPath)
                .orElse(null);
    }

    private int getStartLine(ClassOrInterfaceDeclaration analyzedClass) {
        return analyzedClass.getName().getRange()
                .map(range -> range.begin.line)
                .orElse(0);
    }

    @Override
    public String getPatternName() {
        return "Singleton";
    }
}
