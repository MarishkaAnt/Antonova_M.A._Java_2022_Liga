package org.liga.dao;

import org.liga.model.Task;

import java.util.List;

public interface TaskDAO {

    List<Task> findAllByStatus(String status);
    List<Task> findAll();
}
