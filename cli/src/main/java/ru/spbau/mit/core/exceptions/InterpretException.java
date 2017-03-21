package ru.spbau.mit.core.exceptions;

/**
 * Class that represents exception that may
 * arise during processing tokens to commands
 */
public class InterpretException extends Exception {
    public InterpretException(String message) {
        super(message);
    }
}
