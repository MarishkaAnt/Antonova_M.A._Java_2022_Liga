package org.liga.exception;

public class WrongCommandParameters extends RuntimeException {
    private static final String message = """
            Неверное количество параметров. Пожалуйста, следуйте указаниям инструкции.
            Введите [HELP] для вывода инструкции.
            """;

    public WrongCommandParameters() {
        super(message);
    }

    public WrongCommandParameters(String customMessage) {
        super(customMessage);
    }

}
