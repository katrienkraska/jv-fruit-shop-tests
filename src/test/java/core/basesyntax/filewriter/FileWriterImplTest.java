package core.basesyntax.filewriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriterImpl fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validFile_ok() throws Exception {
        File tempFile = File.createTempFile("output", ".csv");
        String data = "line1" + System.lineSeparator()
                + "line2" + System.lineSeparator() + "line3";
        fileWriter.write(data, tempFile.getPath());
        String result = Files.readString(tempFile.toPath());
        assertEquals(data, result);
    }

    @Test
    void write_invalidPath_throwsException() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.write("data", "invalid/path.csv"));
    }

    @Test
    void write_fileWithoutPermission_throwsException() throws Exception {
        File tempFile = File.createTempFile("restricted", ".csv");
        tempFile.setWritable(false);

        try {
            assertThrows(RuntimeException.class,
                    () -> fileWriter.write("data", tempFile.getPath()));
        } finally {
            tempFile.setWritable(true);
            tempFile.deleteOnExit();
        }
    }
}
