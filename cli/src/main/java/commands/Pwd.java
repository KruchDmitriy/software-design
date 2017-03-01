package commands;

import core.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Pwd extends Command {
    public Pwd(String[] args) {
        super(args);
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        String currentDirectory = System.getProperty("user.dir");
        return new ByteArrayInputStream(currentDirectory.getBytes(StandardCharsets.UTF_8));
    }
}
