package commands;

import core.Environment;

import java.io.InputStream;

public abstract class Command {
    public Command(final String[] args) {}
    public abstract InputStream run(Environment env,
                                    InputStream inputStream) throws CommandException;
}
