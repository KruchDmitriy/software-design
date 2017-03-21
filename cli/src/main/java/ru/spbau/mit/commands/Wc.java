package ru.spbau.mit.commands;

import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Represent a command that counts the number of
 * lines, words, bytes in input stream.
 */
public class Wc implements Command {
    private final String[] fileNames;

    public Wc(String[] args) {
        fileNames = args;
    }

    /**
     * Counts the number of lines, words, bytes in input stream.
     * @param env environment that command executed with
     * @param inputStream where to take arguments for command
     * @return Three values: the number of lines, words, bytes [and file names]
     * if it was read from file.
     * @throws CommandException - if given file not found
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        String answer = "";
        if (fileNames == null) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));
            answer = getCounts(reader);
        } else {
            for (String filename : fileNames) {
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(filename));
                } catch (FileNotFoundException e) {
                    throw new CommandException(
                            "wc: No such file or directory.");
                }

                answer += getCounts(reader) + " " + filename + "\n";
            }
        }

        return new ByteArrayInputStream(answer.getBytes(
                StandardCharsets.UTF_8));
    }

    private String getCounts(BufferedReader reader) throws CommandException {
        try {
            String line;
            int countBytes = 0;
            int countWords = 0;
            int countLines = 0;
            while ((line = reader.readLine()) != null) {
                countBytes += line.getBytes("UTF-8").length;
                countWords += countNonEmpty(line.split("\\s+"));
                countLines++;
            }
            reader.close();

            return countLines + "\t" + countWords + "\t" + countBytes;
        } catch (IOException e) {
            throw new CommandException("wc: Error while reading stream.");
        }
    }

    private int countNonEmpty(String[] strings) {
        int count = 0;
        for (String str : strings) {
            if (str.length() != 0) {
                count++;
            }
        }
        return count;
    }
}
