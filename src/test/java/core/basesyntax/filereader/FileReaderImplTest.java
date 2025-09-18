package core.basesyntax.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReaderImpl fileReader;
    private static final String PATH_TO_REPORT_READ =
            "src/main/resources/reportToRead.csv";
    private static final String PATH_TO_FINAL_READ =
            "src/main/resources/finalReport.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_missingFile_throwsException() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("invalid_path.csv"));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        File tempFile = File.createTempFile("empty", ".csv");
        List<String> result = fileReader.read(tempFile.getPath());
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    void read_fromFileWrongPass_throwException() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                fileReader.read(PATH_TO_FINAL_READ));
        String actual = exception.getMessage();
        String expected = "Error reading file at path :" + PATH_TO_FINAL_READ;
        assertEquals(expected, actual);
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
}
