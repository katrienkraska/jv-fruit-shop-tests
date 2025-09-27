package core.basesyntax.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter = new DataConverterImpl();

    @BeforeAll
    static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction() {
        List<String> input = Arrays.asList(
                "b,banana,20", "p,apple,20");
        List<FruitTransaction> result = dataConverter.convertToTransaction(input);
        assertEquals(2, result.size());
        assertTrue(result.get(0).toString().contains("banana"));
        assertTrue(result.get(0).toString().contains("20"));
    }

    @Test
    void convertToTransaction_invalidData_throwsException() {
        List<String> input = Arrays.asList("invalid data");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(
                Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_nonNumericQuantity_throwsException() {
        List<String> input = List.of("b,banana,abc");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_missingFields_throwsException() {
        List<String> input = List.of("b,banana");
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }
}
