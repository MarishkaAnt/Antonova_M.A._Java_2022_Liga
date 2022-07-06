package org.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.liga.repository.UserRepository;
import org.liga.mapper.UserMapper;
import org.liga.model.User;
import org.liga.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> create(String parametersLine) {
        User user = UserMapper.stringToUser(parametersLine);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> update(Integer id, String parametersLine) {
        userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User newUser = UserMapper.stringToUser(parametersLine);
        newUser.setId(id);
        return Optional.of(userRepository.save(newUser));
    }
}
