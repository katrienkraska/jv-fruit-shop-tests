package core.basesyntax.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements FileRead {

    @Override
    public List<String> read(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException(
                    "Error reading file at path : " + filePath);
        }

        List<String> inputReport = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inputReport.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error reading file at path : " + filePath, e);
        }
        return inputReport;
    }
}
