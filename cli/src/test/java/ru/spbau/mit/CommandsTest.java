package ru.spbau.mit;

import ru.spbau.mit.commands.Grep;
import junit.framework.TestCase;
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
        assertEquals("10\t22\t97 src/test/resources/test2.txt",
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

    @Test
    public void lsSimpleTest() throws CommandException, IOException {
        String arg[] = {"./src/test/resources"};
        Command ls = new Ls(arg);
        InputStream out;

        out = ls.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        assertEquals(3, outLines.size());
    }

    @Test
    public void lsExceptionTest() throws CommandException, IOException {
        String[] args2 = { "/src/non_exist_file" };
        Command ls = new Ls(args2);
        try {
            ls.run(environment, emptyInputStream);
        } catch (CommandException e) {
            assertEquals("ls: No such file or directory.",
                    e.getMessage());
        }
    }

    @Test
    public void cdSimpleTest() throws CommandException, IOException {
        String arg[] = {"src/test/resources"};
        Command cd = new Cd(arg);
        InputStream out;
        cd.run(environment, emptyInputStream);

        Command ls = new Ls(new String[]{});
        out = ls.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        assertEquals(3, outLines.size());
    }


    @Test
    public void cdExceptionTest() throws CommandException, IOException {
        String[] args2 = { "src/non_exist_dir" };
        Command cd = new Cd(args2);
        try {
            cd.run(environment, emptyInputStream);
        } catch (CommandException e) {
            assertEquals("cd: src/non_exist_dir: No such file or directory.",
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

    @Test
    public void grepTestSimple() throws CommandException, IOException {
        String[] result = {"lorem ipsum"};
        String[] args = {"lorem", "src/test/resources/test2.txt"};
        Command grep = new Grep(args);

        InputStream out = grep.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        String[] outArray = new String[outLines.size()];
        outLines.toArray(outArray);
        for (int i = 0; i < result.length; i++) {
            TestCase.assertEquals(result[i], outArray[i]);
        }
    }

    @Test
    public void grepCaseInsensitiveTest() throws CommandException, IOException {
        String[] result = {"lorem ipsum", "LOREM IPSUM",
                "IPSUM LOREMA"};
        String[] args = {"-i", "lorem", "src/test/resources/test2.txt"};
        Command grep = new Grep(args);

        InputStream out = grep.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        String[] outArray = new String[outLines.size()];
        outLines.toArray(outArray);
        for (int i = 0; i < result.length; i++) {
            TestCase.assertEquals(result[i], outArray[i]);
        }
    }


    @Test
    public void grepWordBoundsTest() throws CommandException, IOException {
        String[] result = {"lorem ipsum", "LOREM IPSUM"};
        String[] args = {"-i", "-w", "lorem",
                "src/test/resources/test2.txt"};
        Command grep = new Grep(args);

        InputStream out = grep.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        String[] outArray = new String[outLines.size()];
        outLines.toArray(outArray);
        for (int i = 0; i < result.length; i++) {
            TestCase.assertEquals(result[i], outArray[i]);
        }
    }

    @Test
    public void grepAfterLinesTest() throws CommandException, IOException {
        String[] result = {"quadro je", "sen ti men tale",
                "4 30 28 11 voka voka", "    op op op"};
        String[] args = {"-A", "3", "quadro je",
                "src/test/resources/test2.txt"};
        Command grep = new Grep(args);

        InputStream out = grep.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        String[] outArray = new String[outLines.size()];
        outLines.toArray(outArray);
        TestCase.assertEquals(result.length, outArray.length);
        for (int i = 0; i < result.length; i++) {
            TestCase.assertEquals(result[i], outArray[i]);
        }
    }

    @Test
    public void grepRegExpTest() throws CommandException, IOException {
        String[] result = {"4 30 28 11 voka voka"};
        String[] args = {"\\d+", "src/test/resources/test2.txt"};
        Command grep = new Grep(args);

        InputStream out = grep.run(environment, emptyInputStream);
        List<String> outLines = readFromStream(out);
        String[] outArray = new String[outLines.size()];
        outLines.toArray(outArray);
        for (int i = 0; i < result.length; i++) {
            TestCase.assertEquals(result[i], outArray[i]);
        }
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
