package core;

import com.sun.istack.internal.NotNull;
import commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public final class Shell {
    private static final BufferedReader reader =
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

                String processedLine = Preprocessor.parse(environment, line);
                List<String> tokens = Tokenizer.parse(processedLine);
                List<Command> commands = Interpretator.perform(tokens);
                InputStream stream = Runner.execute(environment, commands);

                if (stream == null) {
                    isEnd = true;
                } else {
                    print(stream);
                }
            } catch (PreprocessException | TokenizeException |
                    InterpretException | RunnerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                isEnd = true;
            }
        } while (isEnd);
    }

    private static String read() throws IOException {
        return reader.readLine();
    }

    private static void print(@NotNull final InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
