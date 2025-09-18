package core.basesyntax.filewriter;

import java.io.File;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validFile_ok() throws Exception {
        File tempFile = File.createTempFile("output", ".csv");
        String data = "line1\nline2\nline3";
        fileWriter.write(data, tempFile.getPath());
        String result = Files.readString(tempFile.toPath());
        Assert.assertEquals(data, result);
    }

    @Test
    void write_invalidPath_throwsException() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileWriter.write("data", "invalid/path.csv"));
    }

    @Test
    void write_fileWithoutPermission_throwsException() throws Exception {
        File tempFile = File.createTempFile("restricted", ".csv");
        tempFile.setWritable(false);

        try {
            Assert.assertThrows(RuntimeException.class,
                    () -> fileWriter.write("data", tempFile.getPath()));
        } finally {
            tempFile.setWritable(true);
            tempFile.deleteOnExit();
        }
    }
}
