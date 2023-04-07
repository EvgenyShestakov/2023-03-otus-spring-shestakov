package ru.otus.homework.exception;

public class ParseException extends RuntimeException {
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
