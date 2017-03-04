package core;

import commands.Command;
import commands.CommandException;
import commands.Exit;

import java.io.InputStream;
import java.util.List;

/**
 * Represents a class that combine commands to a single pipeline.
 */
public final class Runner {
    private static final InputStream STDIN = System.in;

    private Runner() {}

    /**
     * Takes a list of commands from Interpretator and executes them one by one,
     * propagating output of previous command to the input of next command.
     * @param environment - dictionary where (key,value) = (nameVariable,value).
     * @param commands
     * @return output of the last command
     * @throws RunnerException - rethrows command exception.
     */
    public static InputStream execute(Environment environment,
                                      final List<Command> commands)
            throws RunnerException {
        InputStream stream = STDIN;
        for (Command command : commands) {
            if (command instanceof Exit) {
                return null;
            }
            try {
                stream = command.run(environment, stream);
            } catch (CommandException e) {
                throw new RunnerException("Error in command, message:"
                        + e.getMessage());
            }
        }
        return stream;
    }
}
