package org.liga.dao.impl;

import org.liga.dao.UserDAO;
import org.liga.filehandler.Reader;
import org.liga.filehandler.Writer;
import org.liga.filehandler.impl.CsvReader;
import org.liga.filehandler.impl.CsvWriter;
import org.liga.mapper.UserMapper;
import org.liga.model.User;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvUserDAOImpl implements UserDAO {

    private final Path path;
    private final Writer writer = new CsvWriter();
    private final Reader reader = new CsvReader();
    private static List<String> lines = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    public static CsvUserDAOImpl instance = null;

    private CsvUserDAOImpl(Path path) throws IOException {
        this.path = path;
        initialiseUsers();
    }

    public static CsvUserDAOImpl getInstance(Path userCsvPath) throws IOException {
        return new CsvUserDAOImpl(userCsvPath);
    }

    @Override
    public Boolean add(User user) {
        String line = UserMapper.userToString(user);
        lines.add(line);
        try {
            writer.write(lines, path);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> findAll() {
        return lines;
    }

    @Override
    public void deleteAll() throws IOException {
        try {
            writer.write(new ArrayList<>(), path);
        } catch (IOException e) {
            throw new IOException("It is impossible to delete users because of: ", e);
        }
    }

    private void initialiseUsers() throws IOException {
        lines = reader.readFile(path);
    }

}

