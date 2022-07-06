package org.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.liga.repository.TaskRepository;
import org.liga.enums.Status;
import org.liga.mapper.TaskMapper;
import org.liga.model.Task;
import org.liga.service.TaskService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAllByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public void changeStatus(Integer id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        task.setStatus(Status.valueOf(status));
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> create(String parametersLine) {
        Task task = TaskMapper.stringToTask(parametersLine);
        return Optional.of(taskRepository.save(task));
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    @Override
    public void deleteById(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> update(Integer id, String parametersLine) {
        taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Task newTask = TaskMapper.stringToTask(parametersLine);
        newTask.setId(id);
        return Optional.of(taskRepository.save(newTask));
    }
}
