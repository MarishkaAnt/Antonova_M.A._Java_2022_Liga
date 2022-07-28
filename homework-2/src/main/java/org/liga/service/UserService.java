package org.liga.service;

import org.liga.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends AbstractService<User, Integer> {

    @Override
    Optional<User> create(User user);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Integer id);

    @Override
    void deleteAll();

    @Override
    void deleteById(Integer id);

    @Override
    Optional<User> update(Integer id, User user);

}
