package core;

import commands.Command;
import commands.CommandFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that interpretates tokens to a commands
 */
public final class Interpretator {
    private Interpretator() {}

    /**
     * Takes a list of tokens from Tokenizer and translates them to commands.
     * It splits tokens to groups called "tasks" by "|".
     * From every task created a command.
     * @param tokens
     * @return list of commands
     * @throws InterpretException - throws exception if there were no tokens
     * between two pipes.
     */
    public static List<Command> perform(final List<String> tokens)
            throws InterpretException {
        List<Command> commands = new ArrayList<>();
        for (List<String> task : getTasks(tokens)) {
            commands.add(CommandFactory.getCommand(task));
        }
        return commands;
    }

    /**
     * Splits strings by pipe.
     * @param tokens
     * @return list of list of strings
     * @throws InterpretException - throws exception if there were no tokens
     * between two pipes.
     */
    private static List<List<String>> getTasks(final List<String> tokens)
            throws InterpretException {
        List<List<String>> tasks = new ArrayList<>();
        List<String> task = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("|")) {
                if (task.size() == 0) {
                    throw new InterpretException("syntax error near "
                            + "unexpected token '|'");
                }
                tasks.add(task);
                task = new ArrayList<>();
            } else {
                task.add(tokens.get(i));
            }
        }
        if (task.size() != 0) {
            tasks.add(task);
        }

        return tasks;
    }
}
