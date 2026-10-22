package patternvisor;

public interface PatternDetector {
    public AnalysisResult findPatterns(ProjectContext context);
    public String getPatternName();
}
