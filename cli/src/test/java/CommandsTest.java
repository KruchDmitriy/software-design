import commands.*;
import core.Environment;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CommandsTest {
    private Environment environment = new Environment();
    private InputStream emptyInputStream = new ByteArrayInputStream(
            "".getBytes(StandardCharsets.UTF_8));
    private String[] emptyArgs = new String[1];

    @Test
    public void wcTest() {
        String[] args = { "src/test/resources/test.txt",
                "src/test/resources/test2.txt" };
        Command wc = new Wc(args);
        InputStream out;
        try {
            out = wc.run(environment, emptyInputStream);
            List<String> outLines = readFromStream(out);
            assertEquals(2, outLines.size());
            assertEquals("4\t8\t32 src/test/resources/test.txt",
                    outLines.get(0));
            assertEquals("6\t16\t63 src/test/resources/test2.txt",
                    outLines.get(1));
        } catch (CommandException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }

        wc = new Wc(null);
        try {
            InputStream inputStream = new ByteArrayInputStream(
                    "all cats join in..."
                            .getBytes(StandardCharsets.UTF_8));
            out = wc.run(environment, inputStream);
            List<String> outLines = readFromStream(out);
            assertEquals(1, outLines.size());
            assertEquals("1\t4\t19", outLines.get(0));
        } catch (CommandException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }

        String[] args2 = { "/src/c1435xasdfb.txt.json.function.joba.joba" };
        wc = new Wc(args2);
        try {
            wc.run(environment, emptyInputStream);
        } catch (CommandException e) {
            assertEquals("wc: No such file or directory.", e.getMessage());
        }
    }

    @Test
    public void externCommandTest() {
        String[] args = {"ls", "-a"};
        Command cmd = new ExternalCommand(args);
        try {
            InputStream out = cmd.run(environment, emptyInputStream);
            List<String> outLines = readFromStream(out);
            assertEquals(".", outLines.get(0));
            assertEquals("..", outLines.get(1));
            assertEquals("cli.iml", outLines.get(2));
            assertEquals("diagram.png", outLines.get(3));
            assertEquals(".idea", outLines.get(4));
            assertEquals("pom.xml", outLines.get(5));
            assertEquals("src", outLines.get(6));
        } catch (CommandException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }

        String[] args2 = {"strangeProcess.nobodyKnowsIt"};
        cmd = new ExternalCommand(args2);
        try {
            cmd.run(environment, emptyInputStream);
        } catch (CommandException e) {
            assertTrue(true);
        }
    }

    @Test
    public void catTest() {
        String[] result = {"this is a test file", "for", "cat", "utility"};
        String[] args = {"src/test/resources/test.txt"};
        Command cat = new Cat(args);

        try {
            InputStream out = cat.run(environment, emptyInputStream);
            List<String> outLines = readFromStream(out);
            String[] outArray = new String[outLines.size()];
            outLines.toArray(outArray);
            for (int i = 0; i < outArray.length; i++) {
                assertEquals(result[i], outArray[i]);
            }
        } catch (CommandException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    @Test
    public void echoTest() {
        String[] args = {"book", "for", "cat"};
        Command echo = new Echo(args);

        try {
            InputStream out = echo.run(environment, emptyInputStream);
            List<String> outLines = readFromStream(out);
            assertEquals(1, outLines.size());
            assertEquals("book for cat", outLines.get(0));
        } catch (CommandException | IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    @Test
    public void pwdTest() {
        String pwd = System.getProperty("user.dir");

        Command cmd = new Pwd(emptyArgs);
        InputStream resultStream;
        try {
            resultStream = cmd.run(environment, emptyInputStream);
            List<String> lines = readFromStream(resultStream);
            assertEquals(1, lines.size());
            assertEquals(pwd, lines.get(0));
        } catch (CommandException | IOException e){
            System.out.println(e.getMessage());
            assertTrue(false);
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
