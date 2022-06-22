package org.liga.dao;

import org.liga.model.User;

public interface UserDAO {

    Integer add(User user);
    Boolean removeById(Integer id);
}
