package org.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectContext {
    private List<CompilationUnit> allCompilationUnits = new ArrayList<CompilationUnit>();
//    private List<ClassOrInterfaceDeclaration> allClasses = new ArrayList<ClassOrInterfaceDeclaration>();
    private Map<String, ClassOrInterfaceDeclaration> allClasses = new HashMap<>();

    public ProjectContext(String sourcePath){ //path to the analyzed project
        configureSymbolSolver(sourcePath);
        loadFiles(sourcePath);
    }

    private void configureSymbolSolver(String sourcePath){
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();

        combinedTypeSolver.add(new ReflectionTypeSolver());

        combinedTypeSolver.add(new JavaParserTypeSolver(new File(sourcePath)));

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
    }

    private void loadFiles(String sourcePath){
        File root = new File(sourcePath);
        if (!root.isDirectory() || !root.exists()){
            System.out.println("Directory doesnt exist or is not a directory.");
            return;
        }

        processDirectory(root);
    }

    private void processDirectory(File directory){
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processDirectory(file);
                } else if (file.getName().endsWith(".java")) {
                    parseAndStore(file.toPath());
                }
            }
        }
    }

    private void parseAndStore(Path filePath) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(filePath);
            allCompilationUnits.add(cu);

//            allClasses.addAll(cu.findAll(ClassOrInterfaceDeclaration.class));
            cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c ->
                    allClasses.put(c.getNameAsString(),c));

        } catch (IOException e) {
            System.err.println("Błąd parsowania: " + filePath);
        }
    }
}
