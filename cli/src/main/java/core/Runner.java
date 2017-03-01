package core;

import com.sun.istack.internal.NotNull;
import commands.Command;
import commands.CommandException;
import commands.Exit;

import java.io.InputStream;
import java.util.List;

public final class Runner {
    private static final InputStream STDIN = System.in;

    private Runner() {}

    public static InputStream execute(@NotNull Environment environment,
                                      @NotNull final List<Command> commands) throws RunnerException {
        InputStream stream = STDIN;
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i) instanceof Exit) {
                return null;
            }
            try {
                stream = commands.get(i).run(environment, stream);
            } catch (CommandException e) {
                e.printStackTrace();
                throw new RunnerException("Error in command " +
                        commands.get(i).toString());
            }
        }
        return stream;
    }
}
