package org.liga.dao;

import org.liga.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Boolean create(User user);

    List<User> findAll();

    Optional<User> findById(Integer id);

    void deleteAll();

    void deleteById(Integer id);

    void update(User user);
}
