package core.basesyntax.report;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_validInventory_ok() {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        inventory.put("apple", 50);
        inventory.put("banana", 30);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,30" + System.lineSeparator();
        String result = reportGenerator.getReport(inventory);
        assertEquals(expected, result);
    }

    @Test
    void getReport_emptyInventory_returnsEmptyString() {
        String result = reportGenerator.getReport(new LinkedHashMap<>());
        assertEquals("", result);
    }
}
