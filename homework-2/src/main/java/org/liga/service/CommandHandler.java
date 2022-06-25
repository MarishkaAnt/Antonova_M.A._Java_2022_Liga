package org.liga.service;

import org.liga.dao.TaskDAO;
import org.liga.dao.UserDAO;
import org.liga.dao.impl.CsvTaskDOAImpl;
import org.liga.dao.impl.CsvUserDAOImpl;
import org.liga.enums.Commands;
import org.liga.exception.WrongCommandParameters;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandHandler {
    private static final CommandHandler instance = null;
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    private CommandHandler() {
        userDAO = new CsvUserDAOImpl(Path.of("homework-2/src/main/resources/Users.csv"));
        taskDAO = new CsvTaskDOAImpl(Path.of("homework-2/src/main/resources/Tasks.csv"));
    }

    public static CommandHandler getInstance() {
        return Objects.requireNonNullElseGet(instance, CommandHandler::new);
    }

    public void handleCommand(String inputCommand) {

        List<String> commandParameters = Arrays.stream(
                inputCommand.trim().split(",")
        ).toList();
        String command = commandParameters.get(0).trim();
        try {
            Commands.valueOf(command).action(userDAO, taskDAO, commandParameters);
        } catch (WrongCommandParameters e) {
            System.out.println(e + "пожалуйста, попробуйте еще раз");
        } catch (IllegalArgumentException e) {
            System.out.println(e + "Команда не распознается, попробуйте еще раз");
        } catch (RuntimeException e){
            System.out.println("Упс, что-то сломалось...");
        }
    }
}
