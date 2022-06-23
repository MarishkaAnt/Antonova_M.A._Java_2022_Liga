package org.liga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.liga.filehandler.Writer;
import org.liga.filehandler.impl.CsvWriter;

import java.nio.file.Path;
import java.util.List;

public class CsvWriterTest {
    Writer writer = new CsvWriter();

    @Test
    void correctDataFormat_fileCreated() {
        //given
        List<String> lines = List.of("1, тест, тест");
        Path path = Path.of("src/test/resources/EmptyCsvForWriting.csv");

        //then
        Assertions.assertDoesNotThrow(() -> writer.write(lines, path));
    }
}
