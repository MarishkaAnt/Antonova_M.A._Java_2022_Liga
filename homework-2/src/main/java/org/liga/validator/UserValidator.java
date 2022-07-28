package org.liga.validator;

import org.liga.model.User;

import static org.liga.util.StringConstants.USER_COULD_NOT_BE_NULL;
import static org.liga.util.StringConstants.FIELDS_COULD_NOT_BE_NULL;

public class UserValidator{
    public static void validateIfUserNullThrowIAE(User user){
        if(user == null){
            throw new IllegalArgumentException(USER_COULD_NOT_BE_NULL);
        }
    }

    public static void validateIfAnyFieldOfUserNullThrowIAE(User user){
        if(user.getFirstName() == null || user.getLastName() == null){
            throw new IllegalArgumentException(FIELDS_COULD_NOT_BE_NULL);
        }
    }

}
