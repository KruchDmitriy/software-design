package ru.spbau.mit;

import org.junit.Test;
import ru.spbau.mit.commands.*;
import ru.spbau.mit.core.Environment;
import ru.spbau.mit.core.Runner;
import ru.spbau.mit.core.exceptions.RunnerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RunnerTest {
    @Test
    public void simpleTest() throws RunnerException, IOException {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] echoArgs = {"ho", "ho"};
        commands.add(new Echo(echoArgs));
        commands.add(new Wc(null));

        InputStream out = Runner.execute(environment, commands);
        List<String> outLines = readFromStream(out);
        assertEquals(1, outLines.size());
        assertEquals("1\t2\t5", outLines.get(0));
    }

    @Test
    public void externCommandTest() throws RunnerException, IOException {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] extCmd = {"ls", "-a", "src/test/resources/"};
        commands.add(new Echo(null));
        commands.add(new ExternalCommand(extCmd));
        commands.add(new Wc(null));

        InputStream out = Runner.execute(environment, commands);
        List<String> outLines = readFromStream(out);
        assertEquals(1, outLines.size());
        assertEquals("5\t5\t34", outLines.get(0));
    }

    @Test
    public void echoEchoTest() throws RunnerException, IOException {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] echoArgs = {"ho", "ho"};
        commands.add(new Echo(echoArgs));
        commands.add(new Echo(null));

        InputStream out = Runner.execute(environment, commands);
        List<String> outLines = readFromStream(out);
        assertEquals(1, outLines.size());
    }

    @Test
    public void catEchoTest() throws RunnerException, IOException {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] echoArgs = {"ho", "ho"};
        commands.add(new Echo(echoArgs));
        commands.add(new Cat(null));

        InputStream out = Runner.execute(environment, commands);
        List<String> outLines = readFromStream(out);
        assertEquals(1, outLines.size());
        assertEquals("ho ho", outLines.get(0));
    }

    private List<String> readFromStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> data = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        return data;
    }
}
