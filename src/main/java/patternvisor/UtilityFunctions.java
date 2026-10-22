package patternvisor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UtilityFunctions {
    private UtilityFunctions() {
    }

    public static String getQualifiedName(ClassOrInterfaceDeclaration declaration) {
        return declaration.findCompilationUnit()
                .flatMap(CompilationUnit::getPackageDeclaration)
                .map(pkg -> pkg.getNameAsString() + "." + declaration.getNameAsString())
                .orElse(declaration.getNameAsString());
    }
}
