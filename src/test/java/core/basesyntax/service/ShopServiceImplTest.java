package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Storage.getInventory().clear();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        shopService.process(List.of(transaction));

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        Assert.assertEquals(50, inventory.get("apple").intValue());
    }

    @Test
    void process_emptyTransactions_noHandlersCalled() {
        shopService.process(Collections.emptyList());

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        Assert.assertTrue(
                "Inventory should remain empty when processing an empty transaction list",
                inventory.isEmpty());
    }
}
