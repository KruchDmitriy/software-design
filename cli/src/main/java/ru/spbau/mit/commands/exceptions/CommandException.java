package ru.spbau.mit.commands.exceptions;

/**
 * Class that represents exception that may
 * arise during command execution
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
