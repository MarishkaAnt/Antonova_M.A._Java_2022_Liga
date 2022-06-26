package org.liga.exception;

public class WrongCommandParametersException extends RuntimeException {
    private static final String message = """
            Неверное количество параметров. Пожалуйста, следуйте указаниям инструкции.
            Введите [HELP] для вывода инструкции.
            """;

    public WrongCommandParametersException() {
        super(message);
    }

    public WrongCommandParametersException(String customMessage) {
        super(customMessage);
    }

}
