package org.liga.filehandler.impl;

import org.liga.filehandler.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvWriter implements Writer {

    @Override
    public void write(List<String> lines, Path path) throws IOException {
        Files.write(path, lines);
    }
}
