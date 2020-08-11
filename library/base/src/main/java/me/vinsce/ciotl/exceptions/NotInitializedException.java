package me.vinsce.ciotl.exceptions;

public class NotInitializedException extends RuntimeException {
    public NotInitializedException(String message) {
        super(message);
    }

    public NotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }
}
