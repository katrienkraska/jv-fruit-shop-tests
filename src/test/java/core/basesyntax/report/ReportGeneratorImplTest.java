package core.basesyntax.report;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_validInventory_ok() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        inventory.put("apple", 50);
        inventory.put("banana", 30);

        String expected = "apple,50\nbanana,30\n";
        String result = reportGenerator.getReport(inventory);
        Assert.assertEquals(expected, result);
    }

    @Test
    void getReport_emptyInventory_returnsEmptyString() {
        String result = reportGenerator.getReport(new LinkedHashMap<>());
        Assert.assertEquals("", result);
    }
}
