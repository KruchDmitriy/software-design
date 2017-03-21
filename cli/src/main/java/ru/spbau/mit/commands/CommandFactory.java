package ru.spbau.mit.commands;

import java.util.List;
import java.util.stream.Stream;

public final class CommandFactory {
    private static final String[] BUILT_IN_COMMANDS = {
        "cat", "echo", "exit", "pwd", "wc"
    };

    private CommandFactory() {}

    /**
     * Creates command from task
     * @param task must contain the utility name and arguments
     * @return constructed command
     */
    public static Command getCommand(List<String> task) {
        boolean isBuiltInCmd = Stream.of(BUILT_IN_COMMANDS).anyMatch(
                (String str) -> str.equals(task.get(0)));
        final int modifyArgsSize = 2;
        if (isBuiltInCmd) {
            String name = task.get(0);
            task.remove(0);
            String[] args;
            if (task.size() > 0) {
                args = new String[task.size()];
                task.toArray(args);
            } else {
                args = null;
            }

            switch (name) {
                case "cat":
                    return new Cat(args);
                case "echo":
                    return new Echo(args);
                case "exit":
                    return new Exit();
                case "pwd":
                    return new Pwd();
                case "wc":
                    return new Wc(args);
                default:
                    String[] extCmdArgs = new String[task.size()];
                    task.toArray(extCmdArgs);
                    return new ExternalCommand(extCmdArgs);
            }
        } else if (task.size() <= modifyArgsSize) {
            if (task.get(0).contains("=")) {
                String[] args = new String[2];
                String[] split = task.get(0).split("=");
                args[0] = split[0];
                if (task.size() == 1) {
                    args[1] = split[1];
                } else {
                    args[1] = task.get(1);
                }

                return new ModifyEnv(args);
            }
        }

        String[] args = new String[task.size()];
        task.toArray(args);
        return new ExternalCommand(args);
    }
}
