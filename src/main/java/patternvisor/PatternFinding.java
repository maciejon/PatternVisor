package patternvisor;

import java.nio.file.Path;
import java.util.List;

public record PatternFinding(
        String patternName,
        String typeName,
        Path sourceFile,
        int line,
        List<String> evidence
) {
    public PatternFinding {
        evidence = List.copyOf(evidence);
    }
}