package commands;

import core.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Echo extends Command {
    private final String[] args;

    public Echo(String[] args) {
        super(args);
        this.args = args;
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        String concat = "";
        for (String arg : args) {
            concat += arg;
        }
        return new ByteArrayInputStream(concat.getBytes(StandardCharsets.UTF_8));
    }
}
