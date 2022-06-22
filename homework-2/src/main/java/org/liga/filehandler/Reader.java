package org.liga.filehandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Reader {

    List<String> readFile(Path path) throws IOException;
}
