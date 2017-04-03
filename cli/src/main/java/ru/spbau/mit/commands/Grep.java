package ru.spbau.mit.commands;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.InvalidOptionSpecificationException;
import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of grep unix-like utility.
 */
public class Grep implements Command {
    private final String[] args;
    private BufferedReader reader;
    private Pattern pattern;
    private int numLinesAfter;

    public Grep(String[] args) {
        this.args = args;
    }

    private void setupEnvironment(InputStream inputStream)
            throws CommandException {
        GrepArgsParser parser;
        try {
            parser = CliFactory.parseArguments(GrepArgsParser.class, args);
        } catch (ArgumentValidationException
                | InvalidOptionSpecificationException e) {
            throw new CommandException(e.getMessage());
        }

        List<String> unparsedArgs = parser.getUnparsed();
        String stringPattern = unparsedArgs.get(0);
        String filename = null;

        if (unparsedArgs.size() > 1) {
            filename = unparsedArgs.get(1);
        }

        if (parser.isWordRegexp()) {
            stringPattern = "\\b" + stringPattern + "\\b";
        }

        if (parser.isIgnoreCase()) {
            pattern = Pattern.compile(stringPattern,
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        } else {
            pattern = Pattern.compile(stringPattern);
        }

        numLinesAfter = parser.getNumLinesAfter();

        if (filename != null) {
            try {
                reader = new BufferedReader(new FileReader(filename));
            } catch (FileNotFoundException e) {
                throw new CommandException("grep: File not found.");
            }
        } else {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        setupEnvironment(inputStream);

        StringBuilder builder = new StringBuilder();
        try {
            String line;
            int curLineAfter = -1;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    curLineAfter = numLinesAfter;
                }
                if (curLineAfter >= 0) {
                    builder.append(line).append("\n");
                    curLineAfter--;
                }
            }
        } catch (IOException e) {
            throw new CommandException("grep: Error while reading file.");
        }

        return new ByteArrayInputStream(
                builder.toString()
                .getBytes(StandardCharsets.UTF_8));
    }
}
