package core;

import com.sun.istack.internal.NotNull;
import commands.Command;
import commands.CommandFactory;

import java.util.ArrayList;
import java.util.List;

public final class Interpretator {
    private Interpretator() {}
    public static List<Command> perform(@NotNull final List<String> tokens) throws InterpretException {
        List<Command> commands = new ArrayList<>();
        for (List<String> task : getTasks(tokens)) {
            commands.add(CommandFactory.getCommand(task));
        }
        return commands;
    }

    private static List<List<String>> getTasks(final List<String> tokens) throws InterpretException {
        List<List<String>> tasks = new ArrayList<>();
        List<String> task = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("|")) {
                if (task.size() == 0) {
                    throw new InterpretException("syntax error near unexpected token '|'");
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
