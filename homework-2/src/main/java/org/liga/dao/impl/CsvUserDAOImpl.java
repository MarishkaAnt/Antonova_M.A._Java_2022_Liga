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
    private static final String FILE_READING_ERROR = "Извините, что-то не так с файлами для хранения данных :";
    public static final String USER_NOT_FOUND = "Пользователя с таким id не существует";
    private final Path path;
    private List<String> lines = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public CsvUserDAOImpl(Path path) {
        this.path = path;
        try {
            initialiseUsers();
        } catch (IOException e) {
            System.out.println(FILE_READING_ERROR + e.getMessage());
        }
    }

    private void initialiseUsers() throws IOException {
        if (!Files.isReadable(path)) {
            throw new IOException("Невозможно открыть файл: " + path);
        }
        lines.addAll(Files.readAllLines(path));
        if (lines.size() > 0) {
            users.addAll(lines.stream()
                    .map(UserMapper::stringToUser)
                    .collect(Collectors.toList()));
        } else {
            throw new IOException("В файле нет данных " + path + "\n" +
                    "Пожалуйста, добавьте минимум одного пользователя");
        }
    }

    @Override
    public Boolean create(User user) {
        if (!validateUser(user)) {
            return false;
        }
        String line = UserMapper.userToString(user);
        lines.add(line);
        users.add(user);
        try {
            Files.write(path, List.of(line), CREATE, APPEND);
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
                        new WrongCommandParameters(USER_NOT_FOUND));
    }

    @Override
    public void deleteAll() {
        lines.clear();
        users.clear();
        try {
            Files.write(path, new ArrayList<>(), TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("It's impossible to delete users from file: " + path + " because of: ", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        User founded = users.stream()
                .filter(u -> u.getId().equals(id))
                .findAny().orElseThrow(() ->
                        new WrongCommandParameters(USER_NOT_FOUND));
        users.removeAll(List.of(founded));
        lines.removeAll(List.of(UserMapper.userToString(founded)));
        try {
            Files.write(path, lines, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("It's impossible to delete users from file: " + path + " because of: ", e);
        }
    }

    @Override
    public void update(User user) {
        List<User> updated = users.stream()
                .map(u -> {
                    if (u.getId().equals(user.getId())) {
                        lines.remove(UserMapper.userToString(u));
                        lines.add(UserMapper.userToString(user));
                        u.setFirstName(user.getFirstName());
                        u.setLastName(user.getLastName());
                    }
                    return u;
                })
                .collect(Collectors.toList());
        users.clear();
        users.addAll(updated);
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

