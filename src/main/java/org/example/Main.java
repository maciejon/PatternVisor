package org.example;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

//        CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/org/example/test_files/SingletonSimplest.java"));
//        CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/org/example/test_files/SingletonWithHolder.java"));
        CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/org/example/test_files/SingletonWrongPublicConstructor.java"));

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(analyzedClass -> {
            System.out.println("Analizuję klasę: " + analyzedClass.getNameAsString());

            // --------------------------- SZUKANIE SINGLETONA ---------------------------

            // ------------------- WARUNEK NR.1 - prywatny konstruktor -------------------
            boolean warunek_1 = false;
            List<ConstructorDeclaration> constructors = analyzedClass.findAll(ConstructorDeclaration.class);

            if (constructors.isEmpty() ){
                System.out.println("Nie ma konstruktora, to nie singleton.");
                warunek_1 = false;
            }
            for (ConstructorDeclaration constructor : constructors){
                if (!constructor.isPrivate()){
                    System.out.println("Nieprywatny konstruktor! To nie singleton.");
                    warunek_1 = false;
                    break;
                }
                else{
                    System.out.println("Jest prywatny konstruktor. To może być singleton.");
                    warunek_1 = true;
                }
            }
            if(warunek_1){
                System.out.println("Warunek nr.1 spełniony.");
            }

            else{
                System.out.println("Warunek nr.1 nie spełniony.");
            }

            // ------------------- WARUNEK NR.2 - przechowuje Instance typu siebie -------------------
            boolean warunek_2 = false;
            //                                          tutaj findAll zamiast getFields() pozwala znaleźć również pola w klasach zagnieżdżonych
            for (FieldDeclaration field : analyzedClass.findAll(FieldDeclaration.class)){
                if (field.isStatic() && field.isPrivate()){
                    for (VariableDeclarator variable : field.getVariables()){ // w jednym polu może być kilka zmiennych np. int x, y;
                        if(variable.getTypeAsString().equals(analyzedClass.getNameAsString())){
                            warunek_2 = true;
                            System.out.println("Znaleziono prywatną statyczną zmienną o typie takim samym jak nazwa klasy.");
                        }
                    }
                }
            }
            if(warunek_2){
                System.out.println("Warunek nr.2 spełniony.");
            }
            else{
                System.out.println("Warunek nr.2 nie spełniony.");
            }

            // ------------------- WARUNEK NR.3 - metoda statyczna zwracająca instancję -------------------
            boolean warunek_3 = false;
            for (MethodDeclaration method : analyzedClass.getMethods()){
                if (method.isPublic() && method.isStatic() && method.getTypeAsString().equals(analyzedClass.getNameAsString())){
                    warunek_3 = true;
                    // dołożyć sprawdzanie co się dzieje w środku metody?
                    System.out.println("Znaleziono statyczną publiczną metodę, która zwraca typ taki jak jest klasa.");
                }
            }
            if(warunek_3){
                System.out.println("Warunek nr.3 spełniony.");
            }
            else{
                System.out.println("Warunek nr.3 nie spełniony.");
            }

            if(warunek_1 && warunek_2 && warunek_3){
                System.out.println("Mamy singletona!");
            }
            else {
                System.out.println("Nie znaleziono singletona!");
            }
        });
    }
}