package io.github.elihuso.style;

public enum LoggerLevel {
    POSITIVE(0),
    NEGATIVE(1),
    NOTIFICATION(2),
    WARNING(3);

    private final int value;

    LoggerLevel(final int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}
