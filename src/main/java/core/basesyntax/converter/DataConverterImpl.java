package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String COMMA = ",";
    private static final int INDEX_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY = 2;
    private static final int EXPECTED_PARTS_COUNT = 3;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : inputReport) {
            if (line.trim().equalsIgnoreCase("type,fruit,quantity")) {
                continue;
            }

            String[] parts = line.split(COMMA);
            if (parts.length != EXPECTED_PARTS_COUNT) {
                throw new IllegalArgumentException(
                        "Invalid line format: " + line);
            }

            String operationCode = parts[INDEX_OPERATION];
            String fruit = parts[INDEX_FRUIT];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[INDEX_QUANTITY]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Invalid quantity in line: " + line);
            }

            FruitTransaction.Operation operation =
                    FruitTransaction.Operation.getOperation(operationCode);

            transactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactions;
    }
}
