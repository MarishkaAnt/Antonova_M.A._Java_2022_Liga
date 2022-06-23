package org.liga;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.liga.dao.UserDAO;
import org.liga.dao.impl.CsvUserDAOImpl;
import org.liga.model.User;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class CsvUserDAOImplTest {
    private static UserDAO UserDAO;
    private static final String CORRECT_PATH = "src/test/resources/EmptyCsvForWriting.csv";
    private static final String INCORRECT_PATH = "src/test/resources/notExists.csv";
    private static final User EMPTY_USER = User.builder().build();
    private static final User CORRECT_USER = User.builder()
                                                .id(4)
                                                .firstName("name")
                                                .lastName("name")
                                                .build();

    @BeforeAll
    static void init() {
        Path path = Path.of(CORRECT_PATH);
        try {
            UserDAO = CsvUserDAOImpl.getInstance(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void clean() {
        try {
            UserDAO.deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addCorrectUser_nothingThrows() {

        Assertions.assertDoesNotThrow(() -> UserDAO.add(CORRECT_USER));
    }

    @Test
    void addCorrectUser_returnTrue() throws IOException {
        Assertions.assertTrue(UserDAO.add(CORRECT_USER));
    }

    @Test
    void fileNotExist_throwIOException(){

        Assertions.assertThrows(NoSuchFileException.class,
                () -> CsvUserDAOImpl.getInstance(Path.of(INCORRECT_PATH)));
    }

}
