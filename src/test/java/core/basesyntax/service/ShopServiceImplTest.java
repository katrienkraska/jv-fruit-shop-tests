package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.getInventory().clear();
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        shopService.process(List.of(transaction));

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        assertEquals(50, inventory.get("apple").intValue());
    }

    @Test
    void process_purchaseTransaction_ok() {
        Storage.getInventory().put("banana", 30);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10);
        shopService.process(List.of(transaction));

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        assertEquals(20, inventory.get("banana").intValue());
    }

    @Test
    void process_returnTransaction_ok() {
        Storage.getInventory().put("orange", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "orange", 3);
        shopService.process(List.of(transaction));

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        assertEquals(8, inventory.get("orange").intValue());
    }

    @Test
    void process_supplyTransaction_ok() {
        Storage.getInventory().put("kiwi", 0);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "kiwi", 7);
        shopService.process(List.of(transaction));

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        assertEquals(7, inventory.get("kiwi").intValue());
    }

    @Test
    void process_emptyTransactions_noHandlersCalled() {
        shopService.process(Collections.emptyList());

        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        assertTrue(
                "Inventory should remain empty when processing an empty transaction list",
                inventory.isEmpty());
    }

    @Test
    void process_transactionWithNegativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -5);
        assertThrows(RuntimeException.class,
                () -> shopService.process(List.of(transaction)));
    }
}
