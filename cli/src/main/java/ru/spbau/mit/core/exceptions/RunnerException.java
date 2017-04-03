package ru.spbau.mit.core.exceptions;

/**
 * Class that represents exception that may
 * arise during execution of commands
 */
public class RunnerException extends Exception {
    public RunnerException(String message) {
        super(message);
    }
}
