package org.liga.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.liga.exception.WrongCommandParametersException;
import org.liga.mapper.UserMapper;
import org.liga.model.User;
import org.liga.service.UserService;
import org.liga.strategy.ExecuteStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateUserStrategyImpl implements ExecuteStrategy<UserService> {

    @Override
    public String execute(UserService service, List<String> parameters) {
        int requiredAmountOfParams = 3;
        int size = parameters.size();
        String response;
        if (size == requiredAmountOfParams) {
            String parametersLine = parameters.stream()
                    .skip(1)
                    .collect(Collectors.joining(","));
            User userFromString = UserMapper.stringToUser(parametersLine);
            Optional<User> user = service.create(userFromString);
            response = "Пользователь добавлен: " + user.orElseThrow(WrongCommandParametersException::new);
        } else {
            throw new WrongCommandParametersException();
        }
        return response;
    }
}
