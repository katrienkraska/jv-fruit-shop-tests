package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            if (transaction.getQuantity() < 0) {
                throw new RuntimeException("Quantity cannot be negative");
            }
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            handler.handle(Storage.getInventory(), transaction);
        }
    }

    public Map<String, Integer> getInventory() {
        return Storage.getInventory();
    }
}
