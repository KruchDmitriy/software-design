package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Class that represent command that print it's arguments
 * or write it in stream for next command
 */
public class Echo implements Command {
    private final String[] args;

    public Echo(String[] args) {
        this.args = args;
    }

    /**
     * Runs an echo command.
     * @param env environment that command executed with
     * @param inputStream where to take arguments for command
     * @return result of command execution
     * @throws CommandException if there where errors during command execution
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        String concat = "";
        if (args != null) {
            for (int i = 0; i < args.length - 1; i++) {
                concat += args[i] + " ";
            }
            concat += args[args.length - 1];
        }

        concat += "\n";
        return new ByteArrayInputStream(concat.getBytes(StandardCharsets.UTF_8));
    }
}
