package org.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.liga.enums.TaskCommands;
import org.liga.enums.UserCommands;
import org.liga.enums.CommonCommands;
import org.liga.exception.WrongCommandParametersException;
import org.liga.service.TaskService;
import org.liga.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandHandlerService {

    private final UserService userService;
    private final TaskService taskService;

    public String handleCommand(String inputCommand) {

        String response;

        List<String> commandParameters = Arrays.asList(
                inputCommand.trim().split(","));

        try {
            String command = commandParameters.get(0).trim();
            if(command.contains("USER")){
                response = UserCommands.valueOf(command).action(userService, commandParameters);
            }else if(command.contains("TASK")){
                response = TaskCommands.valueOf(command).action(taskService, commandParameters);
            }else{
                response = CommonCommands.valueOf(command).action(commandParameters);
            }
        } catch (WrongCommandParametersException e) {
            response = "Пожалуйста, попробуйте еще раз. " + e.getMessage();
        } catch (IllegalArgumentException e) {
            response = "Команда не распознается, попробуйте еще раз. " + e.getMessage();
        } catch (EntityNotFoundException | EmptyResultDataAccessException e){
            response = "Такая сущность не существует. Подробнее: " + e;
        } catch (RuntimeException e){
            response = "Упс, что-то сломалось... " + e;
        }
        return response;
    }
}
