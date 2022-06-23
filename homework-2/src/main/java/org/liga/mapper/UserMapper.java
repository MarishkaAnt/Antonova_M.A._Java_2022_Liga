package org.liga.mapper;

import org.liga.exception.WrongCommandParameters;
import org.liga.model.User;

import java.util.Arrays;
import java.util.List;

public class UserMapper {

    public static User stringToUser(String userParametersLine) {
        User user;
        List<String> parameters = Arrays.stream(
                userParametersLine.split(",")
        ).toList();
        if (!(parameters.size() < 3)) {
            throw new WrongCommandParameters("Wrong size of parameters");
        }
        try {
            user = User.builder()
                    .id(Integer.parseInt(parameters.get(0)))
                    .firstName(parameters.get(1))
                    .lastName(parameters.get(2))
                    .build();
        } catch (NumberFormatException e) {
            throw new WrongCommandParameters("Wrong type of id, use numbers only");
        }

        return user;
    }

    public static String userToString(User user) {
        return user.getId() + ", "
                + user.getFirstName() + ", "
                + user.getLastName();
    }
}
