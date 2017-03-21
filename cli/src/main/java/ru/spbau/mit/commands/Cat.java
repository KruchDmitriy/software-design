package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Represents unix cat command, if it ran without parameters,
 * it would read from stdin.
 */
public class Cat implements Command {
    private final String[] fileNames;

    public Cat(String[] args) {
        fileNames = args;
    }

    /**
     * Starts execution of a command.
     * @param env environment
     * @param inputStream a stream to read from
     * @return content of a file in inputStream
     * @throws CommandException if file not found
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        if (fileNames == null || fileNames.length == 0) {
            return inputStream;
        } else {
            String lines = "";

            for (String filename : fileNames) {
                try (BufferedReader br = new BufferedReader(
                        new FileReader(filename))) {
                    String line;

                    while ((line = br.readLine()) != null) {
                        lines += line + "\n";
                    }
                } catch (IOException e) {
                    throw new CommandException("cat: " + filename
                            + ": No such file or directory");
                }
            }
            return new ByteArrayInputStream(lines.getBytes(
                    StandardCharsets.UTF_8));
        }
    }
}
