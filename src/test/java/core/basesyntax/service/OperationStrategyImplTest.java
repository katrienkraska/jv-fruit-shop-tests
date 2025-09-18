package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void getHandler_validOperation_ok() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler));
        OperationHandler result = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(balanceHandler, result);
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation()));
        Assert.assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(
                        FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void operationStrategyImpl_emptyMap_throwsException() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> new OperationStrategyImpl(Map.of()));
    }
}
