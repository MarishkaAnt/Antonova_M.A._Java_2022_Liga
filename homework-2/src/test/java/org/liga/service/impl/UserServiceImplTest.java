package org.liga.service.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.liga.model.User;
import org.liga.repository.UserRepository;
import org.liga.service.UserService;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.liga.util.StringConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.liga.service.impl.StringConstantsForTests.*;

class UserServiceImplTest {

    private static UserRepository userRepository;
    private static UserService userService;

    @BeforeAll
    static void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @AfterAll
    static void tearDown() {
        userService = null;
    }

    @Test
    @Tag("update")
    void update_correctUser_OptionalIsPresent() {
        //given
        User correct = getCorrectUser();
        User expected = getCorrectUser();
        expected.setId(CORRECT_ID);
        when(userRepository.save(any(User.class)))
                .thenReturn(correct);
        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(correct));
        //when
        Optional<User> actual = userService.update(CORRECT_ID, correct);
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
        User correctUser = getCorrectUser();
        //then
        assertThatThrownBy(() -> userService.update(id, correctUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ID_COULD_NOT_BE_NULL_OR_NEGATIVE);
    }

    @Test
    @Tag("update")
    void update_nullId_ThrowException() {
        //given
        User correctUser = getCorrectUser();
        //then
        assertThatThrownBy(() -> userService.update(null, correctUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ID_COULD_NOT_BE_NULL_OR_NEGATIVE);
    }

    @Test
    @Tag("update")
    void update_nullUser_ThrowException() {
        //given
        Integer id = CORRECT_ID;
        //then
        assertThatThrownBy(() -> userService.update(id, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(USER_COULD_NOT_BE_NULL);
    }

    @Test
    @Tag("update")
    void update_userNotExist_ThrowException() {
        //given
        User correctUser = getCorrectUser();
        when(userRepository.findById(NOT_EXISTING_ID))
                .thenThrow(EntityNotFoundException.class);
        //then
        assertThatThrownBy(() -> userService.update(NOT_EXISTING_ID, correctUser))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @Tag("delete")
    void deleteById_negativeId_ThrowException() {
        //given
        Integer negativeId = NEGATIVE_ID;
        //then
        assertThatThrownBy(() -> userService.deleteById(negativeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Tag("create")
    void create_correctUser_OptionalIsPresent() {
        //given
        User correct = getCorrectUser();
        when(userRepository.save(any(User.class)))
                .thenReturn(correct);
        //when
        Optional<User> actual = userService.create(correct);
        //then
        assertThat(actual).isPresent();
    }

    @Test
    @Tag("create")
    void create_nullUser_ThrowException() {
        //then
        assertThatThrownBy(() -> userService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(USER_COULD_NOT_BE_NULL);
    }

    @Test
    @Tag("create")
    void create_incorrectUser_ThrowException() {
        //given
        User incorrectUser = getIncorrectUser();
        //then
        assertThatThrownBy(() -> userService.create(incorrectUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FIELDS_COULD_NOT_BE_NULL);
    }

    @Test
    @Tag("find")
    void findById_negativeId_ThrowException() {
        //given
        Integer negativeId = NEGATIVE_ID;
        //then
        assertThatThrownBy(() -> userService.findById(negativeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Tag("find")
    void findById_nullId_ThrowException() {
        //then
        assertThatThrownBy(() -> userService.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }


    private User getCorrectUser() {
        return User.builder()
                .firstName(CORRECT_USER_NAME)
                .lastName(CORRECT_LAST_NAME)
                .build();
    }

    private User getIncorrectUser() {
        return User.builder()
                .firstName(null)
                .lastName(CORRECT_LAST_NAME)
                .build();
    }
}