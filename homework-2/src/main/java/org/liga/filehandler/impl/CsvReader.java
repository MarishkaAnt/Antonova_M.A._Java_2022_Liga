package org.liga.filehandler.impl;

import org.liga.filehandler.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class CsvReader implements Reader {

    @Override
    public List<String> readFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
