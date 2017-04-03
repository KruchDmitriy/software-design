package ru.spbau.mit.core.exceptions;

/**
 * Class that represents exception that may
 * arise during processing raw input string
 */
public class PreprocessorException extends Exception {
    public PreprocessorException(String message) {
        super(message);
    }
}
