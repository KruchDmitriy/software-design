package commands;

import java.util.List;
import java.util.stream.Stream;

public final class CommandFactory {
    private static final String[] BUILT_IN_COMMANDS = {
        "cat", "echo", "exit", "pwd", "wc"
    };

    private CommandFactory() {}

    public static Command getCommand(List<String> task) {
        boolean isBuiltInCmd = Stream.of(BUILT_IN_COMMANDS).anyMatch(
                (String str) -> str.equals(task.get(0)));
        if (isBuiltInCmd) {
            String name = task.get(0);
            task.remove(0);
            String[] args = new String[task.size()];
            task.toArray(args);
            switch (name) {
                case "cat":
                    return new Cat(args);
                case "echo":
                    return new Echo(args);
                case "exit":
                    return new Exit(args);
                case "pwd":
                    return new Pwd(args);
                case "wc":
                    return new Wc(args);
            }
        } else if (task.get(1).equals("=")) {
            if (task.size() == 3) {
                String[] args = {task.get(0), task.get(2)};
                return new ModifyEnv(args);
            }
        }

        String[] args = new String[task.size()];
        task.toArray(args);
        return new ExternCommand(args);
    }
}
