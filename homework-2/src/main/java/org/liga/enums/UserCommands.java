package org.liga.enums;

import org.liga.exception.WrongCommandParametersException;
import org.liga.mapper.UserMapper;
import org.liga.model.User;
import org.liga.service.UserService;
import org.liga.strategy.ExecuteStrategy;
import org.liga.strategy.impl.CreateUserStrategyImpl;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum UserCommands {

    ALL_USERS {
        @Override
        public String action(UserService userService, List<String> parameters) {
            int requiredAmountOfParams = 1;
            StringBuilder response = new StringBuilder();
            if (parameters.size() == requiredAmountOfParams) {
                List<User> users = userService.findAll();
                for (User user : users) {
                    response.append(user.toString()).append(System.lineSeparator());
                }
            } else {
                throw new WrongCommandParametersException();
            }
            return response.toString();
        }
    },

    NEW_USER {
        @Override
        public String action(UserService userService, List<String> parameters) {
            ExecuteStrategy<UserService> strategy = new CreateUserStrategyImpl();
            return strategy.execute(userService, parameters);
        }
    },

    GET_USER {
        @Override
        public String action(UserService userService, List<String> parameters) {
            int requiredAmountOfParams = 2;
            if (parameters.size() != requiredAmountOfParams) {
                throw new WrongCommandParametersException();
            }
            int id = Integer.parseInt(parameters.get(1).trim());
            User founded = userService.findById(id).orElseThrow(EntityNotFoundException::new);
            return "UPDATE_USER, " + UserMapper.userToString(founded);
        }
    },

    UPDATE_USER {
        @Override
        public String action(UserService userService, List<String> parameters) {
            int requiredAmountOfParams = 4;
            int size = parameters.size();
            String response = "";
            if (size == requiredAmountOfParams) {
                int id = Integer.parseInt(parameters.get(1).trim());
                String parametersLine = parameters.stream()
                        .skip(2)
                        .collect(Collectors.joining(","));
                User userFromString = UserMapper.stringToUser(parametersLine);
                Optional<User> user = userService.update(id, userFromString);
                response = "Пользователь обновлен: " + user.orElseThrow(WrongCommandParametersException::new);
            }
            return response;
        }
    },

    DELETE_ALL_USERS {
        @Override
        public String action(UserService userService, List<String> parameters) {
            int requiredAmountOfParams = 1;
            if (parameters.size() == requiredAmountOfParams) {
                userService.deleteAll();
            } else {
                throw new WrongCommandParametersException();
            }
            return "Все пользователи удалены";
        }
    },

    DELETE_USER {
        @Override
        public String action(UserService userService, List<String> parameters) {
            int requiredAmountOfParams = 2;
            String response;
            if (parameters.size() == requiredAmountOfParams) {
                int id = Integer.parseInt(parameters.get(1).trim());
                userService.deleteById(id);
                response = "Пользователь с id = " + id + " удален";
            } else {
                throw new WrongCommandParametersException();
            }
            return response;
        }
    };

    public abstract String action(UserService service, List<String> parameters);
}