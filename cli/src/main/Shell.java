package main;

import java.util.stream.Stream;

public final class Shell {
    private Shell() {}

    public static void main(String[] argv) {
        Environment environment = new Environment();

        boolean isEnd;
        do {
            String line = read();
            isEnd = line == null;

            String processedLine = Preprocessor.parse(environment, line);
            String[] tokens = Tokenizer.parse(processedLine);
            Command[] commands = Interpretator.perform(tokens);
            Stream stream = Runner.execute(environment, commands);
            print(stream);
        } while (isEnd);
    }

    private static String read() {
        return null;
    }

    private static void print(final Stream stream) {

    }
}
