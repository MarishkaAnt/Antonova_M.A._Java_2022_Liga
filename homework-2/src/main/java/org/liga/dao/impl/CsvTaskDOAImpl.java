package org.liga.dao.impl;

import org.liga.dao.TaskDAO;
import org.liga.mapper.TaskMapper;
import org.liga.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvTaskDOAImpl implements TaskDAO {
    private static final String FILE_READING_ERROR = "Извините, что-то не так с файлами для хранения данных";
    private final Path path;
    private List<String> lines = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public CsvTaskDOAImpl(Path path) {
        this.path = path;
        try {
            initialiseTasks();
        } catch (IOException e) {
            System.out.println(FILE_READING_ERROR);
            e.printStackTrace();
        }
    }

    private void initialiseTasks() throws IOException {
        if (!Files.isReadable(path)) {
            throw new IOException("Couldn't read the file: " + path);
        }
        lines.addAll(Files.readAllLines(path));
        tasks.addAll(lines.stream()
                .map(TaskMapper::stringToTask)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Task> findAllByStatus(String status) {
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }
}

