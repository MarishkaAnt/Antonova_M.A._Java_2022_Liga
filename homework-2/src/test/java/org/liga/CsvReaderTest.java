package org.liga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.liga.filehandler.Reader;
import org.liga.filehandler.impl.CsvReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CsvReaderTest {

    Reader reader = new CsvReader();

    @Test
    void correctSample_actualEqualsExpected() throws IOException {
        //given
        Path path = Path.of("src/test/resources/CorrectTestSample.csv");
        List<String> expected = List.of("1, ПервыйТекст, ВторойТекст",
                "2, Текст с пробелом, ТЕКСТ С ЗАГЛАВНЫМИ БУКВАМИ");
        //when
        List<String> actual = reader.readFile(path);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptySample_nothingThrows() {
        //given
        Path path = Path.of("src/test/resources/EmptyTestSample.csv");

        //then
        Assertions.assertDoesNotThrow(() -> reader.readFile(path));
    }

    @Test
    void nullPath_throwIOException(){
        Assertions.assertThrows(IOException.class, () -> reader.readFile(Path.of("")));
    }

}
