package core.basesyntax.filereader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
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
        String nonExistentFile = "non_existing_file.csv";
        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistentFile));
        Assert.assertTrue(exception.getMessage().contains("File not found"));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        File tempFile = File.createTempFile("empty", ".csv");
        List<String> result = fileReader.read(tempFile.getPath());
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    void read_existingFile_ok() throws IOException {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = fileReader.read(PATH_TO_REPORT_READ);
        Assert.assertEquals(expected, actual);
    }
}
