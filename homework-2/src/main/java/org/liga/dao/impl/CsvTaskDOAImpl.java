package org.liga.dao.impl;

import org.liga.dao.TaskDAO;
import org.liga.enums.Status;
import org.liga.exception.WrongCommandParameters;
import org.liga.mapper.TaskMapper;
import org.liga.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

public class CsvTaskDOAImpl implements TaskDAO {
    private static final String FILE_READING_ERROR = "Извините, что-то не так с файлами для хранения данных";
    public static final String TASK_NOT_FOUND = "Задачи с таким id не существует";
    private static final String IMPOSSIBLE_TO_DELETE = "Невозможно удалить задачу из файла: ";
    private final Path path;
    private List<String> lines = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public CsvTaskDOAImpl(Path path) {
        this.path = path;
        try {
            initialiseTasks();
        } catch (IOException e) {
            System.out.println(FILE_READING_ERROR + e.getMessage());
        }
    }

    private void initialiseTasks() throws IOException {
        if (!Files.isReadable(path)) {
            throw new IOException("Невозможно открыть файл: " + path);
        }
        lines.addAll(Files.readAllLines(path));
        if (lines.size() > 0) {
            tasks.addAll(lines.stream()
                    .map(TaskMapper::stringToTask)
                    .collect(Collectors.toList()));
        } else {
            throw new IOException("В файле нет данных " + path + "\n" +
                    "Пожалуйста, добавьте минимум одного пользователя");
        }
    }

    @Override
    public Boolean create(Task task) {
        if (!validate(task)) {
            System.out.println("Все параметры, кроме статуса, должны быть заполнены");
            return false;
        }
        if (findById(task.getId()).isPresent()) {
            update(task);
            return true;
        }
        String line = TaskMapper.taskToString(task);
        lines.add(line);
        tasks.add(task);
        try {
            Files.write(path, List.of(line), CREATE, APPEND);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Task> findAll() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByStatus(String status) {
        return tasks.stream()
                .filter(t -> t.getStatus().toString().equals(status.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findAny();
    }

    @Override
    public void deleteAll() {
        lines.clear();
        tasks.clear();
        try {
            Files.write(path, new ArrayList<>(), TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(IMPOSSIBLE_TO_DELETE + path, e);
        }

    }

    @Override
    public void deleteById(Integer id) {
        Task founded = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findAny().orElseThrow(() ->
                        new WrongCommandParameters(TASK_NOT_FOUND));
        tasks.removeAll(List.of(founded));
        lines.removeAll(List.of(TaskMapper.taskToString(founded)));
        try {
            Files.write(path, lines, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(IMPOSSIBLE_TO_DELETE + path, e);
        }
    }

    @Override
    public void update(Task task) {
        List<Task> updated = tasks.stream()
                .map(t -> {
                    if (t.getId().equals(task.getId())) {
                        lines.remove(TaskMapper.taskToString(t));
                        lines.add(TaskMapper.taskToString(task));
                        t.setName(task.getName());
                        t.setDescription(task.getDescription());
                        t.setUserId(task.getUserId());
                        t.setDeadline(task.getDeadline());
                        t.setStatus(task.getStatus());
                    }
                    return t;
                })
                .collect(Collectors.toList());
        tasks.clear();
        tasks.addAll(updated);
        try {
            Files.write(path, lines, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(IMPOSSIBLE_TO_DELETE + path, e);
        }
    }

    @Override
    public void changeStatus(Integer id, String status) {
        Task founded = findById(id).orElseThrow(WrongCommandParameters::new);
        founded.setStatus(Status.valueOf(status));
        update(founded);
    }

    private Boolean validate(Task task) {
        return task.getId() != null &&
                task.getName() != null &&
                task.getDescription() != null &&
                task.getUserId() != null &&
                task.getDeadline() != null;
    }
}

