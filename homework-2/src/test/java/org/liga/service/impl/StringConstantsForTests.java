package org.liga.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringConstantsForTests {
    static final String ALL_USERS = "ALL_USERS";
    static final String ALL_USERS_RESPONSE = "1. Иван Иванов" + System.lineSeparator();
    public static final String NEW_USER = "NEW_USER, Иван, Иванов";
    public static final String NEW_USER_RESPONSE = "Пользователь добавлен: 1. Иван Иванов";
    public static final String UPDATE_USER = "UPDATE_USER, 1, Иван, Романов";
    public static final String GET_USER = "GET_USER, 1";
    public static final String DELETE_USER = "DELETE_USER, 1";
    public static final String DELETE_ALL_USERS = "DELETE_ALL_USERS";

    public static final String ALL_TASKS = "ALL_TASKS";
    public static final String ALL_TASKS_RESPONSE = "1. Иван Иванов - 1. Создать задание: Создать новое задание " +
            "- (NEW) - userId = 1, deadline: 23.11.2022" + System.lineSeparator();
    public static final String ALL_TASKS_WITH_STATUS = "ALL_TASKS, DONE";
    public static final String ALL_TASKS_WITH_STATUS_RESPONSE = "1. Иван Иванов - 1. Создать задание: Создать новое задание " +
            "- (DONE) - userId = 1, deadline: 23.11.2022" + System.lineSeparator();
    public static final String NEW_TASK = "NEW_TASK, Создать задание, Создать новое задание, 1, 23.11.2022, NEW";
    public static final String NEW_TASK_RESPONSE = "Задача добавлена: 1. Создать задание: Создать новое задание - (NEW) - userId = 1, deadline: 23.11.2022";
    public static final String UPDATE_TASK = "UPDATE_TASK, 1, Создать задание, Создать новое задание, 1, 25.11.2022";
    public static final String GET_TASK = "GET_TASK, 1";
    public static final String DELETE_TASK = "DELETE_TASK, 1";
    public static final String DELETE_ALL_TASKS = "DELETE_ALL_TASKS";
    public static final String CHANGE_STATUS = "CHANGE_STATUS, 1, IN_PROGRESS";

    public static final String NOT_EXISTING_COMMAND = "AA";
    public static final String NOT_EXISTING_COMMAND_RESPONSE = "Команда не распознается, попробуйте еще раз. " +
            "No enum constant org.liga.enums.CommonCommands.AA";

    public static final String CORRECT_USER_NAME = "Иван";
    public static final String CORRECT_LAST_NAME = "Иванов";
    public static final int CORRECT_ID = 1;
    public static final int NEGATIVE_ID = -1;
    public static final int NOT_EXISTING_ID = 9999;
    public static final String CORRECT_TASK_NAME = "Создать задание";
    public static final String CORRECT_TASK_DESCRIPTION = "Создать новое задание";
    public static final String ADDITIONAL_PARAMETER = ", additional parameter";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final LocalDate CORRECT_TASK_DEADLINE = LocalDate.of(2022, 11, 23);

}
