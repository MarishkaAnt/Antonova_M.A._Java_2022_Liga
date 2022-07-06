package org.liga.service;

import org.liga.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> create(String parametersLine);

    List<User> findAll();

    Optional<User> findById(Integer id);

    void deleteAll();

    void deleteById(Integer id);

    Optional<User> update(Integer id, String parametersLine);

}
