package core.basesyntax.report;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String COMMA = ",";

    @Override
    public String getReport(Map<String, Integer> inventory) {
        StringBuilder report = new StringBuilder();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            report.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append("\n");
        }
        return report.toString();
    }
}
