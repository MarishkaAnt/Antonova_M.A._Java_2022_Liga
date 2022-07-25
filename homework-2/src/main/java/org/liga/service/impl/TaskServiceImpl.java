package org.liga.service.impl;

import org.liga.repository.TaskRepository;
import org.liga.enums.Status;
import org.liga.model.Task;
import org.liga.service.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.liga.validator.IdValidator.*;
import static org.liga.validator.TaskValidator.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(@Qualifier("TaskRepository") TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAllByStatus(Status status) {
        validateIfStatusNotInRangeOfValues(status);
        return taskRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public void changeStatus(Integer id, Status status) {
        validateIfStatusNotInRangeOfValues(status);
        validateIfIdNullOrNegativeThrowIAE(id);
        Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        task.setStatus(status);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>((Collection<? extends Task>) taskRepository.findAll());
    }

    @Override
    public Optional<Task> findById(Integer id) {
        validateIfIdNullOrNegativeThrowIAE(id);
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> create(Task task) {
        validateIfTaskNullThrowIAE(task);
        validateIfAnyFieldOfTaskNullThrowIAE(task);
        return Optional.of(taskRepository.save(task));
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        validateIfIdNullOrNegativeThrowIAE(id);
        taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Task> update(Integer id, Task newTask) {
        validateIfTaskNullThrowIAE(newTask);
        validateIfAnyFieldOfTaskNullThrowIAE(newTask);
        validateIfIdNullOrNegativeThrowIAE(id);
        taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        newTask.setId(id);
        return Optional.of(taskRepository.save(newTask));
    }
}
