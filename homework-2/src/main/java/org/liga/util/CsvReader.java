package org.liga.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class CsvReader {

    public static List<String> readLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
