package org.liga.service.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.liga.enums.Status;
import org.liga.model.Task;
import org.liga.model.User;
import org.liga.repository.TaskRepository;
import org.liga.service.TaskService;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.liga.util.StringConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.liga.service.impl.StringConstantsForTests.*;

class TaskServiceImplTest {

    private static TaskRepository taskRepository;
    private static TaskService taskService;

    @BeforeAll
    static void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @AfterAll
    static void tearDown() {
        taskService = null;
    }

    @Test
    @Tag("update")
    void update_correctTask_OptionalIsPresent() {
        //given
        Task correct = getCorrectTask();
        Task expected = getCorrectTask();
        expected.setId(CORRECT_ID);
        when(taskRepository.save(any(Task.class)))
                .thenReturn(correct);
        when(taskRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(correct));
        //when
        Optional<Task> actual = taskService.update(CORRECT_ID, correct);
        //then
        assertThat(actual).isPresent()
                .get()
                .satisfies(a -> assertThat(a.getId()).isEqualTo(expected.getId()));
    }

    @Test
    @Tag("update")
    void update_negativeId_ThrowException() {
        //given
        Integer id = NEGATIVE_ID;
        Task correctTask = getCorrectTask();
        //then
        assertThatThrownBy(() -> taskService.update(id, correctTask))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ID_COULD_NOT_BE_NULL_OR_NEGATIVE);
    }

    @Test
    @Tag("update")
    void update_nullId_ThrowException() {
        //given
        Task correctTask = getCorrectTask();
        //then
        assertThatThrownBy(() -> taskService.update(null, correctTask))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ID_COULD_NOT_BE_NULL_OR_NEGATIVE);
    }

    @Test
    @Tag("update")
    void update_nullTask_ThrowException() {
        //given
        Integer id = CORRECT_ID;
        //then
        assertThatThrownBy(() -> taskService.update(id, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TASK_COULD_NOT_BE_NULL);
    }

    @Test
    @Tag("update")
    void update_userNotExist_ThrowException() {
        //given
        Task correctTask = getCorrectTask();
        when(taskRepository.findById(NOT_EXISTING_ID))
                .thenThrow(EntityNotFoundException.class);
        //then
        assertThatThrownBy(() -> taskService.update(NOT_EXISTING_ID, correctTask))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Tag("delete")
    void deleteById_negativeId_ThrowException() {
        //given
        Integer negativeId = NEGATIVE_ID;
        //then
        assertThatThrownBy(() -> taskService.deleteById(negativeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Tag("create")
    void create_correctTask_OptionalIsPresent() {
        //given
        Task correct = getCorrectTask();
        when(taskRepository.save(any(Task.class)))
                .thenReturn(correct);
        //when
        Optional<Task> actual = taskService.create(correct);
        //then
        assertThat(actual).isPresent();
    }

    @Test
    @Tag("create")
    void create_nullTask_ThrowException() {
        //then
        assertThatThrownBy(() -> taskService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TASK_COULD_NOT_BE_NULL);
    }

    @Test
    @Tag("create")
    void create_incorrectTask_ThrowException() {
        //given
        Task incorrectTask = getIncorrectTask();
        //then
        assertThatThrownBy(() -> taskService.create(incorrectTask))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FIELDS_COULD_NOT_BE_NULL);
    }

    @Test
    @Tag("find")
    void findById_negativeId_ThrowException() {
        //given
        Integer negativeId = NEGATIVE_ID;
        //then
        assertThatThrownBy(() -> taskService.findById(negativeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Tag("find")
    void findById_nullId_ThrowException() {
        //then
        assertThatThrownBy(() -> taskService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }


    private Task getCorrectTask() {
        return Task.builder()
                .name(CORRECT_TASK_NAME)
                .description(CORRECT_TASK_DESCRIPTION)
                .user(getCorrectUser())
                .status(Status.NEW)
                .deadline(LocalDate.now())
                .build();
    }

    private User getCorrectUser() {
        return User.builder()
                .firstName(CORRECT_USER_NAME)
                .lastName(CORRECT_LAST_NAME)
                .build();
    }

    private Task getIncorrectTask() {
        return Task.builder()
                .name(null)
                .description(CORRECT_TASK_DESCRIPTION)
                .user(getCorrectUser())
                .status(Status.NEW)
                .deadline(LocalDate.now())
                .build();
    }
}