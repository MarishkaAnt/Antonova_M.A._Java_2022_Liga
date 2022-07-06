package org.liga.service;

import org.liga.enums.Status;
import org.liga.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAllByStatus(Status status);

    void changeStatus(Integer id, String status);

    List<Task> findAll();

    Optional<Task> findById(Integer id);

    Optional<Task> create(String parametersLine);

    void deleteAll();

    void deleteById(Integer id);

    Optional<Task> update(Integer id, String parametersLine);

}
