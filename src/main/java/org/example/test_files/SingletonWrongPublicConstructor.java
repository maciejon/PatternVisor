package org.example.test_files;

public class SingletonWrongPublicConstructor {
    private SingletonWrongPublicConstructor(){};
    public SingletonWrongPublicConstructor(int a){};
    private static SingletonWrongPublicConstructor instance;

    public static SingletonWrongPublicConstructor getInstance() {
        if (instance == null){
            return instance = new SingletonWrongPublicConstructor();
        }
        else {
            return instance;
        }
    }
}