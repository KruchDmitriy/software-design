package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Represents a command that can print current working directory.
 */
public class Pwd implements Command {
    /**
     * Run a command.
     * @param env environment that command executed with
     * @param inputStream where to take arguments for command
     * @return result of command execution
     * @throws CommandException if there where errors during command execution
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        if (env.read("PWD").isEmpty()) {
            env.write("PWD", System.getProperty("user.dir"));
        }
        String currentDirectory = env.read("PWD");
        return new ByteArrayInputStream(currentDirectory.getBytes(
                StandardCharsets.UTF_8));
    }
}
