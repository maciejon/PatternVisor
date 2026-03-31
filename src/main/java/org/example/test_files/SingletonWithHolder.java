package org.example.test_files;

public class SingletonWithHolder {
    private SingletonWithHolder() {}

    private static class SingletonHolder {
        private static final SingletonWithHolder INSTANCE = new SingletonWithHolder();
    }

    public static SingletonWithHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
