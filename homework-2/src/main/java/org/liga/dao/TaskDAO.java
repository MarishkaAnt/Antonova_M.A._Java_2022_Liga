package org.liga.dao;

import org.liga.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDAO {

    List<Task> findAllByStatus(String status);

    void changeStatus(Integer id, String status);

    List<Task> findAll();

    Optional<Task> findById(Integer id);

    Boolean create(String parametersLine);

    void deleteAll();

    void deleteById(Integer id);

    void update(Task task);
}
