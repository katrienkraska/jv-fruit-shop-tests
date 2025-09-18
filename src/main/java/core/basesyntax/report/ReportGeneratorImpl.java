package core.basesyntax.report;

import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String getReport(Map<String, Integer> inventory) {
        if (inventory.isEmpty()) {
            return "";
        }

        StringBuilder report = new StringBuilder();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            report.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append("\n");
        }
        return report.toString();
    }
}
