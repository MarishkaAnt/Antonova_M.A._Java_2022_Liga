package org.liga.utest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.liga.model.User;
import org.liga.repository.UserRepository;
import org.liga.service.UserService;
import org.liga.service.impl.UserServiceImpl;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private static UserRepository userRepository;
    private static UserService userService;

    @BeforeAll
    static void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void update_correctUser_OptionalIsPresent() {
        //given
        Integer id = 1;
        User correct = getCorrectUser();
        User expected = getCorrectUser();
        expected.setId(id);
        when(userRepository.save(any(User.class)))
                .thenReturn(correct);
        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(correct));
        //when
        Optional<User> actual = userService.update(1, correct);
        //then
        assertThat(actual).isPresent()
                .get()
                .satisfies(a -> assertThat(a.getId()).isEqualTo(expected.getId()));
    }

    @Test
    void update_negativeId_ThrowException() {
        //given
        Integer id = -1;
        User correctUser = getCorrectUser();
        String expectedMessage = "id less or equals zero";
        //then
        assertThatThrownBy(() -> userService.update(id, correctUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void update_nullId_ThrowException() {
        //given
        User correctUser = getCorrectUser();
        String expectedMessage = "id is null";
        //then
        assertThatThrownBy(() -> userService.update(null, correctUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void update_nullUser_ThrowException() {
        //given
        Integer id = 1;
        String expectedMessage = "user is null";
        //then
        assertThatThrownBy(() -> userService.update(id, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private User getCorrectUser() {
        return User.builder()
                .firstName("John")
                .lastName("Douh")
                .build();
    }
}