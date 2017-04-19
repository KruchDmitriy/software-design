package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Implementation of cd unix-like utility.
 * Change current directory.
 */
public class Cd implements Command {
    private final String arg;

    public Cd(String[] args) {
        arg = args.length == 0 ? null : args[0];
    }

    /**
     *
     * @param env environment that command executed with
     * @param inputStream where to take arguments for command
     * @return empty Input Stream. Change value of PWD variable in Environment
     * @throws CommandException - if given directory doesn't exist.
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        if (arg == null) {
            env.write("PWD", System.getProperty("user.home"));
        } else {
            File path = new File(arg);
            if (path.exists()) {
                env.write("PWD", path.getAbsolutePath());
            } else {
                throw new CommandException("cd: " + path + ": No such file or directory.");
            }
        }
        return new ByteArrayInputStream("".getBytes());
    }
}
