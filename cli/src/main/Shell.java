package main;

import java.util.List;
import java.util.stream.Stream;

public final class Shell {
    private Shell() {}

    public static void main(String[] argv) {
        Environment environment = new Environment();

        boolean isEnd;
        do {
            String line = read();
            isEnd = line == null;

            try {
                String processedLine = Preprocessor.parse(environment, line);
                List<String> tokens = Tokenizer.parse(processedLine);
                List<Command> commands = Interpretator.perform(tokens);
                Stream stream = Runner.execute(environment, commands);
                print(stream);
            } catch (PreprocessException | TokenizeException e) {
                e.printStackTrace();
            }
        } while (isEnd);
    }

    private static String read() {
        return null;
    }

    private static void print(final Stream stream) {

    }
}
