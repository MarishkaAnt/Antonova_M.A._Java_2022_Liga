package org.liga.validator;

import org.liga.enums.Status;
import org.liga.model.Task;

import java.util.Arrays;

import static org.liga.util.StringConstants.TASK_COULD_NOT_BE_NULL;
import static org.liga.util.StringConstants.FIELDS_COULD_NOT_BE_NULL;
import static org.liga.util.StringConstants.UNKNOWN_STATUS;

public class TaskValidator {
    public static void validateIfTaskNullThrowIAE(Task task){
        if(task == null){
            throw new IllegalArgumentException(TASK_COULD_NOT_BE_NULL);
        }
    }

    public static void validateIfAnyFieldOfTaskNullThrowIAE(Task task){
        if(task.getName() == null
                || task.getDescription() == null
                || task.getDeadline() == null
                || task.getStatus() == null){
            throw new IllegalArgumentException(FIELDS_COULD_NOT_BE_NULL);
        }
    }
    public static void validateIfStatusNotInRangeOfValues(Status status){
        if(!Arrays.asList(Status.values()).contains(status)){
            throw new IllegalArgumentException(UNKNOWN_STATUS);
        }
    }

}
