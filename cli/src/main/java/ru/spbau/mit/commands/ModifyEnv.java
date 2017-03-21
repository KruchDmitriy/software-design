package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Write or replace environment variable command.
 */
public class ModifyEnv implements Command {
    private final String name;
    private final String value;

    public ModifyEnv(String[] args) {
        name = args[0];
        value = args[1];
    }

    /**
     * Run a modify environment command.
     * @param env environment to write or read to
     * @param inputStream where to take an arguments for command (stay unused)
     * @return result of command execution - produce no output
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        env.write(name, value);
        return new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8));
    }
}
