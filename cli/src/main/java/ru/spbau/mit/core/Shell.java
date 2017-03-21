package ru.spbau.mit.core;

import ru.spbau.mit.commands.Command;
import ru.spbau.mit.core.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Main class that combine all classes to a single pipeline.
 * Represents a shell that read from stdin line by line translates
 * commands and executes them.
 */
public final class Shell {
    private static final BufferedReader READER =
            new BufferedReader(new InputStreamReader(System.in));

    private Shell() {}

    public static void main(String[] argv) {
        Environment environment = new Environment();
        String currentDirectory = System.getProperty("user.dir");
        environment.write("PWD", currentDirectory);

        boolean isEnd = true;
        do {
            try {
                String line = read();
                isEnd = line == null;
                if (isEnd) {
                    break;
                }

                String processedLine = Preprocessor.parse(environment, line);
                List<String> tokens = Tokenizer.parse(processedLine);
                List<Command> commands = Interpreter.interpret(tokens);
                InputStream stream = Runner.execute(environment, commands);

                if (stream == null) {
                    isEnd = true;
                } else {
                    print(stream);
                }
            } catch (PreprocessorException | TokenizeException
                    | InterpretException | RunnerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                isEnd = true;
            }
        } while (!isEnd);
    }

    /**
     * Read line from stdin
     * @return line string
     * @throws IOException
     */
    private static String read() throws IOException {
        return READER.readLine();
    }

    /**
     * Print the output of executed commands
     * @param stream - inputStream of executed commands
     */
    private static void print(final InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
