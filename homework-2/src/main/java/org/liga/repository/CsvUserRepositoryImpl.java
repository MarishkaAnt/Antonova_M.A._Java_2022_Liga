package org.liga.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.liga.exception.WrongCommandParametersException;
import org.liga.mapper.UserMapper;
import org.liga.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;
import static org.liga.util.StringConstants.*;
@Repository("CsvUserRepository")
@RequiredArgsConstructor
public class CsvUserRepositoryImpl implements UserRepository {

    @Value("${csv.user}")
    private String stringPath;
    private Path path;
    @Getter
    private static List<String> lines = new ArrayList<>();

    @Getter
    private static List<User> users = new ArrayList<>();


    @PostConstruct
    public void init() throws IOException {
        path = Path.of(stringPath);
        lines.addAll(Files.readAllLines(path));
        if (lines.size() > 0) {
            users.addAll(lines.stream()
                    .map(UserMapper::csvStringToUser).toList());
        }
    }

    @Override
    public User save(@NotNull User user) {
        int nextId = getNextId();
        user.setId(nextId);
        if (!validate(user)) {
            System.out.println("Все параметры должны быть заполнены");
            return null;
        }
        if (findById(user.getId()).isPresent()) {
            return update(user);
        }
        String line = UserMapper.userToString(user);
        lines.add(line);
        users.add(user);
        try {
            Files.write(path, List.of(line), CREATE, APPEND);
        } catch (IOException e) {
            System.out.println("Не удалось записать задачу в файл - " + e);
            return null;
        }
        return user;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return users.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllById(Iterable<Integer> ids) {
        List<User> foundedUsers = new ArrayList<>();
        ids.forEach(i -> foundedUsers.add(
                findById(i).orElseThrow(EntityNotFoundException::new)
        ));
        return foundedUsers;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findAny();
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public void deleteAll() {
        lines.clear();
        users.clear();
        try {
            Files.write(path, new ArrayList<>(), TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(IMPOSSIBLE_TO_DELETE + path, e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        User founded = users.stream()
                .filter(u -> u.getId().equals(id))
                .findAny().orElseThrow(() ->
                        new WrongCommandParametersException(USER_NOT_FOUND));
        users.removeAll(List.of(founded));
        lines.removeAll(List.of(UserMapper.userToString(founded)));
        try {
            Files.write(path, lines, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(IMPOSSIBLE_TO_DELETE + path, e);
        }
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    public User update(User user) {
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
            throw new RuntimeException(IMPOSSIBLE_TO_DELETE + path, e);
        }
        return user;
    }

    private Boolean validate(User user) {
        return user.getId() != null &&
                user.getFirstName() != null &&
                user.getLastName() != null;
    }

    private int getNextId() {
        List<User> sortedUsers = users.stream()
                .sorted(Comparator.comparing(User::getId)).toList();
        int nextId = 1;
        if(users.size() > 0) {
            nextId = (sortedUsers.get(users.size() - 1).getId()) + 1;
        }
        return nextId;
    }

}

