package org.liga.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.liga.enums.Status;
import org.liga.model.Task;
import org.liga.model.User;
import org.liga.service.TaskService;
import org.liga.service.UserService;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.liga.service.impl.StringConstantsForTests.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommandHandlerServiceTest {

    private static UserService userService;
    private static TaskService taskService;
    private static CommandHandlerService commandHandlerService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        taskService = Mockito.mock(TaskService.class);
        commandHandlerService = new CommandHandlerService(userService, taskService);
    }

    @AfterEach
    void tearDown() {
        taskService = null;
        userService = null;
    }

    @Test
    @Tag("user_command")
    void handleCommand_ALL_USERS_rightResponse() {
        //given
        User correctUser = getCorrectUser();
        when(userService.findAll()).thenReturn(List.of(correctUser));
        //when
        String response = commandHandlerService.handleCommand(ALL_USERS);
        //then
        assertThat(response).isEqualTo(ALL_USERS_RESPONSE);
    }

    @Test
    @Tag("task_command")
    void handleCommand_ALL_TASKS_rightResponse() {
        //given
        Task correctTask = getCorrectTask();
        when(taskService.findAll()).thenReturn(List.of(correctTask));
        //when
        String response = commandHandlerService.handleCommand(ALL_TASKS);
        //then
        assertThat(response).isEqualTo(ALL_TASKS_RESPONSE);
    }

    @Test
    @Tag("task_command")
    void handleCommand_ALL_TASKS_WITH_STATUS_rightResponse() {
        //given
        Task correctTask = getCorrectTask();
        correctTask.setStatus(Status.DONE);
        when(taskService.findAllByStatus(Status.DONE)).thenReturn(List.of(correctTask));
        //when
        String response = commandHandlerService.handleCommand(ALL_TASKS_WITH_STATUS);
        //then
        assertThat(response).isEqualTo(ALL_TASKS_WITH_STATUS_RESPONSE);
    }

    @Test
    @Tag("task_command")
    void handleCommand_ALL_TASKS_WITH_STATUS_WithAdditionalParameter_rightResponse() {
        //given
        String wrongAmountOfParametersCommand = ALL_TASKS_WITH_STATUS + ADDITIONAL_PARAMETER;
        //then

        assertThatNoException().isThrownBy(() -> commandHandlerService.handleCommand(wrongAmountOfParametersCommand));
    }

    @Test
    @Tag("user_command")
    void handleCommand_NEW_USER_rightResponse() {
        //given
        User correctUser = getCorrectUser();
        when(userService.create(any())).thenReturn(Optional.ofNullable(correctUser));
        //when
        String response = commandHandlerService.handleCommand(NEW_USER);
        //then
        assertThat(response).isEqualTo(NEW_USER_RESPONSE);
    }

    @Test
    @Tag("task_command")
    void handleCommand_NEW_TASK_rightResponse() {
        //given
        Task correctTask = getCorrectTask();
        when(taskService.create(any())).thenReturn(Optional.ofNullable(correctTask));
        //when
        String response = commandHandlerService.handleCommand(NEW_TASK);
        //then
        assertThat(response).isEqualTo(NEW_TASK_RESPONSE);
    }

    @Test
    @Tag("negative_case")
    void handleCommand_NOT_EXISTING_rightResponse() {
        //when
        String response = commandHandlerService.handleCommand(NOT_EXISTING_COMMAND);
        //then
        assertThat(response).isEqualTo(NOT_EXISTING_COMMAND_RESPONSE);
    }

    private User getCorrectUser() {
        return User.builder()
                .id(CORRECT_ID)
                .firstName(CORRECT_USER_NAME)
                .lastName(CORRECT_LAST_NAME)
                .build();
    }

    private Task getCorrectTask() {
        return Task.builder()
                .id(CORRECT_ID)
                .name(CORRECT_TASK_NAME)
                .description(CORRECT_TASK_DESCRIPTION)
                .status(Status.NEW)
                .deadline(CORRECT_TASK_DEADLINE)
                .user(getCorrectUser())
                .build();
    }

}