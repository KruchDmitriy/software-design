package commands;

import core.Environment;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Represents unix cat command, if it ran without parameters,
 * it would read from stdin.
 */
public class Cat extends Command {
    private final String[] filenames;

    public Cat(String[] args) {
        super(args);
        filenames = args;
    }

    /**
     * Starts execution of a command.
     * @param env - environment
     * @param inputStream
     * @return content of a file or inputStream
     * @throws CommandException if file not found
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        if (filenames == null || filenames.length == 0) {
            return inputStream;
        } else {
            String lines = "";

            for (String filename : filenames) {
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
