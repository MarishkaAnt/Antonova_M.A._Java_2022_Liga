package org.liga.service;

import org.liga.enums.Status;
import org.liga.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService extends AbstractService<Task, Integer>{

    List<Task> findAllByStatus(Status status);

    void changeStatus(Integer id, Status status);

    @Override
    List<Task> findAll();

    @Override
    Optional<Task> findById(Integer id);

    @Override
    Optional<Task> create(Task task);

    @Override
    void deleteAll();

    @Override
    void deleteById(Integer id);

    @Override
    Optional<Task> update(Integer id, Task task);

}
