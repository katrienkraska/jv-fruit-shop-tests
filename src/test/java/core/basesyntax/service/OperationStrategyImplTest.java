package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private OperationHandler balanceHandler;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();
        operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler));
    }

    @Test
    void getHandler_validOperation_ok() {
        OperationHandler result = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler, result);
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void operationStrategyImpl_emptyMap_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new OperationStrategyImpl(Map.of()));
    }

    @Test
    void getHandler_nullOperation_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(null));
    }
}
