package ru.spbau.mit;

import org.junit.Test;
import ru.spbau.mit.commands.*;
import ru.spbau.mit.commands.exceptions.CommandException;
import ru.spbau.mit.core.Environment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandsTest {
    private Environment environment = new Environment();
    private InputStream emptyInputStream = new ByteArrayInputStream(
            "".getBytes(StandardCharsets.UTF_8));

    @Test
    public void wcSimpleTest() throws CommandException, IOException {
        String[] args = {"src/test/resources/test.txt",
                "src/test/resources/test2.txt"};
        Command wc = new Wc(args);
        InputStream out;

        out = wc.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        assertEquals(2, outLines.size());
        assertEquals("4\t8\t32 src/test/resources/test.txt",
                outLines.get(0));
        assertEquals("6\t16\t63 src/test/resources/test2.txt",
                outLines.get(1));
    }

    @Test
    public void wcFromStreamTest() throws CommandException, IOException {
        Command wc = new Wc(null);
        InputStream out;
        InputStream inputStream = new ByteArrayInputStream(
                "all cats join in..."
                        .getBytes(StandardCharsets.UTF_8));
        out = wc.run(environment, inputStream);
        List<String> outLines = readFromStream(out);
        assertEquals(1, outLines.size());
        assertEquals("1\t4\t19", outLines.get(0));
    }

    @Test
    public void wcExceptionTest() throws CommandException, IOException {
        String[] args2 = { "/src/c1435xasdfb.txt.json.function.joba.joba" };
        Command wc = new Wc(args2);
        try {
            wc.run(environment, emptyInputStream);
        } catch (CommandException e) {
            assertEquals("wc: No such file or directory.",
                    e.getMessage());
        }
    }

    @Test(expected = CommandException.class)
    public void externCommandTest() throws CommandException, IOException {
        String[] args = {"ls", "-a", "src/test/resources/"};
        Command cmd = new ExternalCommand(args);

        InputStream out = cmd.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        assertEquals(".", outLines.get(0));
        assertEquals("..", outLines.get(1));
        assertEquals("checkstyle.xml", outLines.get(2));
        assertEquals("test2.txt", outLines.get(3));
        assertEquals("test.txt", outLines.get(4));

        String[] args2 = {"strangeProcess.nobodyKnowsIt"};
        cmd = new ExternalCommand(args2);
        cmd.run(environment, emptyInputStream);
    }

    @Test
    public void catTest() throws CommandException, IOException {
        String[] result = {"this is a test file", "for", "cat", "utility"};
        String[] args = {"src/test/resources/test.txt"};
        Command cat = new Cat(args);

        InputStream out = cat.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        String[] outArray = new String[outLines.size()];
        outLines.toArray(outArray);
        for (int i = 0; i < outArray.length; i++) {
            assertEquals(result[i], outArray[i]);
        }
    }

    @Test
    public void echoTest() throws CommandException, IOException {
        String[] args = {"book", "for", "cat"};
        Command echo = new Echo(args);

        InputStream out = echo.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        assertEquals(1, outLines.size());
        assertEquals("book for cat", outLines.get(0));
    }

    @Test
    public void pwdTest() throws CommandException, IOException {
        String pwd = System.getProperty("user.dir");

        Command cmd = new Pwd();
        InputStream resultStream = cmd.run(environment, emptyInputStream);
        List<String> lines = readFromStream(resultStream);
        assertEquals(1, lines.size());
        assertEquals(pwd, lines.get(0));
    }

    private List<String> readFromStream(InputStream inputStream)
            throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        List<String> data = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        return data;
    }
}
