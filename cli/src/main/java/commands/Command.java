package commands;

import core.Environment;

import java.io.InputStream;

/**
 * Abstract class that represents a command.
 * We create command passes to it arguments.
 * Ones it created we can run this command
 * passes to it environment and input stream.
 */
public abstract class Command {
    public Command(final String[] args) {}
    public abstract InputStream run(Environment env,
                                    InputStream inputStream) throws CommandException;
}
