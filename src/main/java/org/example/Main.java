package org.example;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ProjectContext context = new ProjectContext("src/test_files");
        SingletonDetector singletonDetector = new SingletonDetector();
        singletonDetector.findPatterns(context);

    }
}