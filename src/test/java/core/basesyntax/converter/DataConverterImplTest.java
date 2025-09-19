package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction() {
        List<String> input = Arrays.asList(
                "b,banana,20", "p,apple,20");
        List<FruitTransaction> result = dataConverter.convertToTransaction(input);
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.get(0).toString().contains("banana"));
        Assert.assertTrue(result.get(0).toString().contains("20"));
    }

    @Test
    void convertToTransaction_invalidData_throwsException() {
        List<String> input = Arrays.asList("invalid data");
        Assert.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(
                Collections.emptyList());
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_nonNumericQuantity_throwsException() {
        List<String> input = List.of("b,banana,abc");
        Assert.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_missingFields_throwsException() {
        List<String> input = List.of("b,banana");
        Assert.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }
}
