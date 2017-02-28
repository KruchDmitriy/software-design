package main;

import java.io.InputStream;
import java.util.stream.Stream;

public abstract class Command {
    public Command(final String[] args) {}
    public abstract Stream run(Environment env, InputStream inputStream);
}
