import commands.*;
import core.InterpretException;
import core.Interpretator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class InterpretatorTest {
    @Test
    public void simpleTest() {
        try {
            String[] tokens = {
                    "pwd", "|", "cat", "file.txt", "|", "wc", "-l",
                    "|", "y2", "=", "27", "|", "echo", "hello",
                    "|", "ls", "-a"
            };
            List<Command> commands = Interpretator.perform(Arrays.asList(tokens));
            assertTrue(commands.get(0) instanceof Pwd);
            assertTrue(commands.get(1) instanceof Cat);
            assertTrue(commands.get(2) instanceof Wc);
            assertTrue(commands.get(3) instanceof ModifyEnv);
            assertTrue(commands.get(4) instanceof Echo);
            assertTrue(commands.get(5) instanceof ExternCommand);
        } catch (InterpretException e) {
            assertTrue(false);
        }

        try {
            String[] tokens = {"|"};
            Interpretator.perform(Arrays.asList(tokens));
        } catch (InterpretException e) {
            assertTrue(true);
        }

        try {
            String[] tokens = {"|", "echo"};
            Interpretator.perform(Arrays.asList(tokens));
        } catch (InterpretException e) {
            assertTrue(true);
        }

        try {
            String[] tokens = {"cat", "|", "|"};
            Interpretator.perform(Arrays.asList(tokens));
        } catch (InterpretException e) {
            assertTrue(true);
        }
    }
}
