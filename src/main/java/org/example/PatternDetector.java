package org.example;

public interface PatternDetector {
    //do zmiany z voida, dorobic jakąś klasę do zwracania struktur wzorców
    public void findPatterns(ProjectContext context);
    public String getPatternName();
}
