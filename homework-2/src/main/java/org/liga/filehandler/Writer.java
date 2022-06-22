package org.liga.filehandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Writer {

    void write(List<String> lines, Path path) throws IOException;
}
