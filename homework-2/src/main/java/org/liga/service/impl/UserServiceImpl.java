package org.liga.service.impl;

import org.liga.repository.UserRepository;
import org.liga.model.User;
import org.liga.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.liga.validator.IdValidator.*;
import static org.liga.validator.UserValidator.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(@Qualifier("UserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> create(User user) {
        validateIfUserNullThrowIAE(user);
        validateIfAnyFieldOfUserNullThrowIAE(user);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>((Collection<User>) userRepository.findAll());
    }

    @Override
    public Optional<User> findById(Integer id) {
        validateIfIdNullOrNegativeThrowIAE(id);
        return userRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteById(Integer id) {
        validateIfIdNullOrNegativeThrowIAE(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> update(Integer id, User user) {
        validateIfUserNullThrowIAE(user);
        validateIfAnyFieldOfUserNullThrowIAE(user);
        validateIfIdNullOrNegativeThrowIAE(id);
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setId(id);
        return Optional.of(userRepository.save(user));
    }
}
