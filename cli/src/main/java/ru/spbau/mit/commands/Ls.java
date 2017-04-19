package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of ls unix-like utility.
 * List all files in directory
 */
public class Ls implements Command {
    private final String path;
    public Ls(String[] args) {
        if (args == null) {
            path = null;
        } else {
            path = args.length == 0 ? null : args[0];
        }
    }

    /**
     * @param env environment that command executed with
     * @param inputStream where to take arguments for command
     * @return the list of files in directory
     * @throws CommandException - if given directory doesn't exist.
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        File curDir = null;
        if (path == null) {
            curDir = new File(env.read("PWD"));
        } else {
            curDir = new File(path);
        }
        final String ansiReset = "\u001B[0m";
        final String ansiBlue = "\u001B[34m";
        final StringBuilder answer = new StringBuilder();
        File[] filesList = curDir.listFiles();
        if (filesList == null) {
            throw new CommandException("ls: No such file or directory.");
        }
        for (File f : filesList) {
            if (f.isDirectory()) {
                answer.append(ansiBlue);
                answer.append(f.getName());
                answer.append(ansiReset);
            }
            if (f.isFile()) {
                answer.append(f.getName());
            }
            answer.append(System.lineSeparator());
        }
        return new ByteArrayInputStream(answer.toString().getBytes(
                StandardCharsets.UTF_8));
    }
}
