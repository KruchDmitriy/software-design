package commands;

import core.Environment;

import java.io.InputStream;

public class Exit extends Command {
    public Exit(String[] args) {
        super(args);
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        return null;
    }
}
