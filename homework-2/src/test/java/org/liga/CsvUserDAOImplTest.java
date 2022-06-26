package org.liga;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.liga.dao.UserDAO;
import org.liga.dao.impl.CsvUserDAOImpl;
import org.liga.mapper.UserMapper;
import org.liga.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static org.junit.jupiter.api.Assertions.*;

public class CsvUserDAOImplTest {
    private static final String CORRECT_PATH = "src/test/resources/CorrectTestSample.csv";
    private static final String INCORRECT_PATH = "src/test/resources/notExists.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/EmptyTestSample.csv";
    private static final User EMPTY_USER = User.builder().build();
    private static final List<String> initialLines = List.of("1, ПервыйТекст, ВторойТекст",
            "2, Текст с пробелом, ТЕКСТ С ЗАГЛАВНЫМИ БУКВАМИ");
    private static final User CORRECT_USER = User.builder()
            .id(4)
            .firstName("name")
            .lastName("name")
            .build();
    private static final User NEW_CORRECT_USER = User.builder()
            .id(4)
            .firstName("new name")
            .lastName("new name")
            .build();

    @AfterEach
    public void clean() throws IOException {
        Files.write(Path.of(CORRECT_PATH), initialLines);
    }

    @Test
    void addCorrectUser_nothingThrows() {
        //given
        UserDAO userDAO = new CsvUserDAOImpl(Path.of(CORRECT_PATH));
        //then
        assertDoesNotThrow(() -> userDAO.create(CORRECT_USER));
    }

    @Test
    void addCorrectUser_returnTrue() {
        //given
        UserDAO userDAO = new CsvUserDAOImpl(Path.of(CORRECT_PATH));
        //then
        assertTrue(userDAO.create(CORRECT_USER));
    }

    @Test
    void addCorrectUser_userAdded() throws IOException {
        //given
        Path path = Path.of(CORRECT_PATH);
        UserDAO userDAO = new CsvUserDAOImpl(path);
        Files.write(path, List.of(UserMapper.userToString(CORRECT_USER)), APPEND);
        List<String> expected = Files.readAllLines(path);
        Files.write(path, initialLines);
        //when
        userDAO.create(CORRECT_USER);
        List<String> actual = Files.readAllLines(path);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void findAll_actualEqualsExpected() throws IOException {
        //given
        Path localPath = Path.of("src/test/resources/CorrectTestSample.csv");
        UserDAO localUserDAO = new CsvUserDAOImpl(localPath);
        List<String> lines = Files.readAllLines(localPath);
        List<User> expected = lines.stream()
                .map(UserMapper::stringToUser)
                .collect(Collectors.toList());
        //when
        List<User> actual = localUserDAO.findAll();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void addEmptyUser_returnFalse() {
        //given
        UserDAO userDAO = new CsvUserDAOImpl(Path.of(CORRECT_PATH));
        //then
        assertFalse(userDAO.create(EMPTY_USER));
    }

    @Test
    void fileNotExist_notingThrow() {
        assertDoesNotThrow(() -> new CsvUserDAOImpl(Path.of(INCORRECT_PATH)));
    }

    @Test
    void emptyFileInitialisation_notingThrow() {
        assertDoesNotThrow(() -> new CsvUserDAOImpl(Path.of(EMPTY_FILE_PATH)));
    }

    @Test
    void addCorrectUserToEmptyFile_returnTrue() {
        //given
        CsvUserDAOImpl localUserDao = new CsvUserDAOImpl(Path.of(EMPTY_FILE_PATH));
        //when
        Boolean isCreated = localUserDao.create(CORRECT_USER);
        localUserDao.deleteAll();
        //then
        assertTrue(isCreated);
    }

    @Test
    void deleteById_correctId_nothingThrow() {
        //given
        Path path = Path.of(CORRECT_PATH);
        UserDAO userDAO = new CsvUserDAOImpl(path);
        //then
        assertDoesNotThrow(() -> userDAO.deleteById(2));
    }

    @Test
    void update_correctNewUser_userUpdated() {
        //given
        Path path = Path.of(CORRECT_PATH);
        UserDAO userDAO = new CsvUserDAOImpl(path);
        userDAO.deleteAll();
        userDAO.create(CORRECT_USER);
        //when
        userDAO.update(NEW_CORRECT_USER);
        List<User> actual = userDAO.findAll();
        //then
        assertEquals(List.of(NEW_CORRECT_USER), actual);
    }

}
