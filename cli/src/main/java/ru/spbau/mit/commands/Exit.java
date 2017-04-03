package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.InputStream;

/**
 * Shutdown shell command.
 */
public class Exit implements Command {
    /**
     * Runs an exit command.
     * @param env environment that command executed with (this parameter
     *            ignored)
     * @param inputStream where to take arguments for command (this parameter
     *            ignored)
     * @return result of command execution (always null)
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        return null;
    }
}
