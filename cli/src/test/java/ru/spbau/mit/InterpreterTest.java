package ru.spbau.mit;

import org.junit.Test;
import ru.spbau.mit.commands.*;
import ru.spbau.mit.core.Interpreter;
import ru.spbau.mit.core.exceptions.InterpretException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class InterpreterTest {
    @Test
    public void simpleTest() throws InterpretException {
        String[] tokens = {
                "pwd", "|", "cat", "file.txt", "|", "wc", "-l",
                "|", "y2=27", "|", "echo", "hello",
                "|", "ls", "-a"
        };
        List<Command> commands = Interpreter.perform(Arrays.asList(tokens));
        assertTrue(commands.get(0) instanceof Pwd);
        assertTrue(commands.get(1) instanceof Cat);
        assertTrue(commands.get(2) instanceof Wc);
        assertTrue(commands.get(3) instanceof ModifyEnv);
        assertTrue(commands.get(4) instanceof Echo);
        assertTrue(commands.get(5) instanceof ExternalCommand);
    }

    @Test(expected = InterpretException.class)
    public void throwTest1() throws InterpretException {
        String[] tokens = {"|"};
        Interpreter.perform(Arrays.asList(tokens));
    }

    @Test(expected = InterpretException.class)
    public void throwTest2() throws InterpretException {
        String[] tokens = {"|", "echo"};
        Interpreter.perform(Arrays.asList(tokens));
    }

    @Test(expected = InterpretException.class)
    public void throwTest3() throws InterpretException {
        String[] tokens = {"cat", "|", "|"};
        Interpreter.perform(Arrays.asList(tokens));
    }
}
