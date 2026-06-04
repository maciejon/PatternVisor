package patternvisor;

public interface PatternDetector {
    //pewnie do zmiany z boola, dorobic jakąś klasę do zwracania struktur wzorców?
    public boolean findPatterns(ProjectContext context);
    public String getPatternName();
}
