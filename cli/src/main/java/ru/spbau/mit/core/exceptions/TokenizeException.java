package ru.spbau.mit.core.exceptions;

/**
 * Class that represents exception that may
 * arise during splitting string to independent
 * tokens
 */
public class TokenizeException extends Exception {
    public TokenizeException(String message) {
        super(message);
    }
}
