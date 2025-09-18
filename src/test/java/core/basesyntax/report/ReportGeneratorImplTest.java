package core.basesyntax.report;

import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_validInventory_ok() {
        Map<String, Integer> inventory = Map.of(
                "apple", 50,
                "banana", 30
        );
        String expected = "apple,50\nbanana,30\n";
        String result = reportGenerator.getReport(inventory);
        Assert.assertEquals(expected, result);
    }

    @Test
    void getReport_emptyInventory_returnsEmptyString() {
        String result = reportGenerator.getReport(Map.of());
        Assert.assertEquals("", result);
    }
}
