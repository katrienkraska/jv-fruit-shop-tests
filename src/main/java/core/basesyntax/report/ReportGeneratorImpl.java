package core.basesyntax.report;

import java.util.Map;
import java.util.TreeMap;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String getReport(Map<String, Integer> inventory) {
        if (inventory.isEmpty()) {
            return "";
        }

        Map<String, Integer> sortedInventory = new TreeMap<>(inventory);
        StringBuilder report = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedInventory.entrySet()) {
            report.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append("\n");
        }
        return report.toString();
    }
}
