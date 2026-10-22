package patternvisor;

import java.util.List;

public record AnalysisResult(
        List<PatternFinding> findings
) {
    public AnalysisResult {
        findings = List.copyOf(findings);
    }

    public boolean hasFindings() {
        return !findings.isEmpty();
    }

    public void printFindings() {
        if (findings.isEmpty()) {
            System.out.println("No patterns found.");
            return;
        }

        System.out.println("Found patterns:");
        for (PatternFinding finding : findings) {
            String sourceFile = finding.sourceFile() == null
                    ? "<unknown file>"
                    : finding.sourceFile().toString();
            String location = finding.line() > 0
                    ? sourceFile + ":" + finding.line()
                    : sourceFile;

            System.out.printf("- %s in %s (%s)%n",
                    finding.patternName(), finding.typeName(), location);

            for (String evidence : finding.evidence()) {
                System.out.printf("    Evidence: %s%n", evidence);
            }
        }
    }
}
