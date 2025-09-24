package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void getHandler_validOperation_ok() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler));
        OperationHandler result = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler, result);
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation()));
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(
                        FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void operationStrategyImpl_emptyMap_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new OperationStrategyImpl(Map.of()));
    }

    @Test
    void getHandler_nullOperation_throwsException() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation()));
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(null));
    }
}
