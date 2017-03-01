package commands;

import core.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ModifyEnv extends Command {
    private final String name;
    private final String value;

    public ModifyEnv(String[] args) {
        super(args);
        name = args[0];
        value = args[1];
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        env.write(name, value);
        return new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8));
    }
}
