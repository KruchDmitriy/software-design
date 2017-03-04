import commands.*;
import core.Environment;
import core.Runner;
import core.RunnerException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RunnerTest {
    @Test
    public void simpleTest() {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] echoArgs = {"ho", "ho"};
        commands.add(new Echo(echoArgs));
        commands.add(new Wc(null));
        try {
            InputStream out = Runner.execute(environment, commands);
            List<String> outLines = readFromStream(out);
            assertEquals(1, outLines.size());
            assertEquals("1\t2\t5", outLines.get(0));
        } catch (RunnerException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    @Test
    public void externCommandTest() {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] extCmd = {"ls", "-a", "src/test/resources/"};
        commands.add(new Echo(null));
        commands.add(new ExternalCommand(extCmd));
        commands.add(new Wc(null));
        try {
            InputStream out = Runner.execute(environment, commands);
            List<String> outLines = readFromStream(out);
            assertEquals(1, outLines.size());
            assertEquals("5\t5\t34", outLines.get(0));
        } catch (RunnerException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    @Test
    public void echoEchoTest() {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] echoArgs = {"ho", "ho"};
        commands.add(new Echo(echoArgs));
        commands.add(new Echo(null));
        try {
            InputStream out = Runner.execute(environment, commands);
            List<String> outLines = readFromStream(out);
            assertEquals(1, outLines.size());
        } catch (RunnerException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    @Test
    public void catEchoTest() {
        Environment environment = new Environment();
        List<Command> commands = new ArrayList<>(2);

        String[] echoArgs = {"ho", "ho"};
        commands.add(new Echo(echoArgs));
        commands.add(new Cat(null));

        try {
            InputStream out = Runner.execute(environment, commands);
            List<String> outLines = readFromStream(out);
            assertEquals(1, outLines.size());
            assertEquals("ho ho", outLines.get(0));
        } catch (RunnerException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
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
