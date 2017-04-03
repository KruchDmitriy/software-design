package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;
import java.io.InputStream;

/**
 * An interface that represents a command.
 * We create command passes to it arguments.
 * Ones it created we can run this command
 * passes to it environment and input stream.
 */
public interface Command {
    /**
     * Run a command.
     * @param env environment that command executed with
     * @param inputStream where to take arguments for command
     * @return result of command execution
     * @throws CommandException if there where errors during command execution
     */
    InputStream run(Environment env, InputStream inputStream)
            throws CommandException;
}
