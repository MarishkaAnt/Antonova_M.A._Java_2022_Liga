package org.liga.enums;

public enum Status {
    NEW,
    IN_PROGRESS,
    DONE;

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }
}
