package org.liga.dao;

import org.liga.model.User;

import java.io.IOException;
import java.util.List;

public interface UserDAO {

    Boolean add(User user) throws IOException;

    List<String> findAll();

    void deleteAll() throws IOException;
}
