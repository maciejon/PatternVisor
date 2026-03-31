package org.example.test_files;

public class SingletonSimplest {
    private SingletonSimplest(){};
    private static SingletonSimplest instance;

    public static SingletonSimplest getInstance() {
        if (instance == null){
            return instance = new SingletonSimplest();
        }
        else {
            return instance;
        }
    }
}