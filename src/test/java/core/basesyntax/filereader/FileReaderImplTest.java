package core.basesyntax.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReaderImpl fileReader;
    private static final String PATH_TO_REPORT_READ =
            "src/main/resources/reportToRead.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_missingFile_throwsException() {
        String invalidPath = "src/main/resources/nonexistent.csv";
        assertThrows(RuntimeException.class,
                () -> fileReader.read(invalidPath));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        File tempFile = File.createTempFile("empty", ".csv");
        List<String> result = fileReader.read(tempFile.getPath());
        assertTrue(result.isEmpty());
        tempFile.deleteOnExit();
    }

    @Test
    void read_existingFile_ok() throws IOException {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");

        List<String> actual = fileReader.read(PATH_TO_REPORT_READ);
        assertEquals(expected, actual);
    }

    @Test
    void read_fileWithSpaces_trimsLines() throws IOException {
        File tempFile = File.createTempFile("spaces", ".csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("type,fruit,quantity   \n");
            writer.write("b,banana,20   \n");
        }
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        List<String> actual = fileReader.read(tempFile.getPath());
        assertEquals(expected, actual);
        tempFile.deleteOnExit();
    }
}
