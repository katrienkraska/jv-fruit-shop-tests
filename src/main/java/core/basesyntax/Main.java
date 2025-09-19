package core.basesyntax;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.filereader.FileReaderImpl;
import core.basesyntax.filewriter.FileWriter;
import core.basesyntax.filewriter.FileWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.report.ReportGenerator;
import core.basesyntax.report.ReportGeneratorImpl;
import core.basesyntax.service.BalanceOperation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.PurchaseOperation;
import core.basesyntax.service.ReturnOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_REPORT_READ =
            "src/main/resources/reportToRead.csv";
    private static final String PATH_TO_FINAL_READ =
            "src/main/resources/finalReport.csv";

    public static void main(String[] arg) {
        FileReaderImpl fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read(PATH_TO_REPORT_READ);

        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport(((
                ShopServiceImpl) shopService).getInventory());

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, PATH_TO_FINAL_READ);
    }
}
