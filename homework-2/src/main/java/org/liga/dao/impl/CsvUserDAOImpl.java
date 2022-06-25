package org.liga.dao.impl;

import org.liga.dao.UserDAO;
import org.liga.exception.WrongCommandParameters;
import org.liga.mapper.UserMapper;
import org.liga.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

public class CsvUserDAOImpl implements UserDAO {
    private static final String FILE_READING_ERROR = "Извините, что-то не так с файлами для хранения данных";
    private final Path path;
    private List<String> lines = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public CsvUserDAOImpl(Path path) {
        this.path = path;
        try {
            initialiseUsers();
        } catch (IOException e) {
            System.out.println(FILE_READING_ERROR);
            e.printStackTrace();
        }
    }

    private void initialiseUsers() throws IOException {
        if (!Files.isReadable(path)) {
            throw new IOException("Couldn't read the file: " + path);
        }
        lines.addAll(Files.readAllLines(path));
        users.addAll(lines.stream()
                .map(UserMapper::stringToUser)
                .collect(Collectors.toList()));
    }

    @Override
    public Boolean create(User user) {
        if (!validateUser(user)) {
            return false;
        }
        String line = UserMapper.userToString(user);
        lines.add(line);
        try {
            Files.write(path, lines, CREATE, APPEND);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(Integer id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findAny().orElseThrow(() ->
                        new WrongCommandParameters("Пользователя с таким id не существует"));
    }

    @Override
    public void deleteAll(){
        try {
            Files.write(path, lines, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("It's impossible to delete users from file: " + path + " because of: ", e);
        }
    }

    private Boolean validateUser(User user) {
        return user.getId() != null &&
                user.getFirstName() != null &&
                user.getLastName() != null;
    }

}

