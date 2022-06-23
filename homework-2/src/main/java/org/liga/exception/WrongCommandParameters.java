package org.liga.exception;

public class WrongCommandParameters extends RuntimeException {

    public WrongCommandParameters() {
        super();
    }

    public WrongCommandParameters(String message) {
        super(message);
    }

}
