package org.liga.mapper;

import org.liga.enums.Status;
import org.liga.exception.WrongCommandParametersException;
import org.liga.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class TaskMapper {

    public static Task stringToTask(String taskParametersLine) {
        Task task;
        List<String> parameters = Arrays.stream(
                taskParametersLine.split(",")
        ).map(String::trim)
                .toList();
        if (parameters.size() < 5) {
            throw new WrongCommandParametersException();
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            task = Task.builder()
                    .id(Integer.parseInt(parameters.get(0)))
                    .name(parameters.get(1))
                    .description(parameters.get(2))
                    .userId(Integer.parseInt(parameters.get(3)))
                    .deadline(LocalDate.parse(parameters.get(4), formatter))
                    .build();
            if (parameters.size() == 6) {
                task.setStatus(Status.valueOf(parameters.get(5)));
            }
        } catch (NumberFormatException e) {
            throw new WrongCommandParametersException("Неверный тип id, используйте только цифры");
        }

        return task;
    }

    public static String taskToString(Task task) {
        return task.getId() + ", "
                + task.getName() + ", "
                + task.getDescription() + ", "
                + task.getUserId() + ", "
                + task.getDeadline() + ", "
                + task.getStatus();
    }

}
