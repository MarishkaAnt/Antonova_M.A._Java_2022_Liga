package org.liga.validator;

import org.liga.util.StringConstants;

public class IdValidator {
    public static void validateIfIdNullOrNegativeThrowIAE(Integer id){
        if(id == null || id < 0){
            throw new IllegalArgumentException(StringConstants.ID_COULD_NOT_BE_NULL_OR_NEGATIVE);
        }
    }

}
