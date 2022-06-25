package org.liga.dao;

import org.liga.model.User;

import java.io.IOException;
import java.util.List;

public interface UserDAO {

    Boolean create(User user);

    List<User> findAll();

    User findById(Integer id);

    void deleteAll();
}
