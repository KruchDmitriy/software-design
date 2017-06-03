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
     * Runs cd command.
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
            if (path.isAbsolute()) {
                if (!path.exists()) {
                    throw new CommandException("cd: " + path + ": No such file or directory.");
                }
                env.write("PWD", path.getAbsolutePath());
            } else {
                String resultPath = env.read("PWD");
                if (arg.equals("..")) {
                    resultPath = resultPath.substring(0, resultPath.lastIndexOf(File.separator));
                    if (resultPath.isEmpty()) {
                        resultPath = File.separator;
                    }
                } else {
                    if (!arg.equals(".")) {
                        if (resultPath.lastIndexOf(File.separator) == resultPath.length() - 1) {
                            resultPath += arg;
                        } else {
                            resultPath += File.separator + arg;
                        }
                    }
                }
                if (!new File(resultPath).exists()) {
                    throw new CommandException("cd: " + resultPath
                            + ": No such file or directory.");
                }
                env.write("PWD", resultPath);
            }
        }
        return new ByteArrayInputStream("".getBytes());
    }
}
