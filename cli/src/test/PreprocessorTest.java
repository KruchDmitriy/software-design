package test;

import main.Environment;
import main.PreprocessException;
import main.Preprocessor;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PreprocessorTest {
    @Test
    public void testSimple() {
        Environment env = new Environment();
        env.write("a", "42");
        env.write("difficultName", "someString");
        try {
            assertTrue(Preprocessor.parse(env, "$a").equals("42"));
            assertTrue(Preprocessor.parse(env, "$a + 1").equals("42 + 1"));
            assertTrue(Preprocessor.parse(env, "$b").equals(""));
            assertTrue(Preprocessor.parse(env, "'$b'").equals("'$b'"));
            assertTrue(Preprocessor.parse(env, "\"$a\"").equals("\"42\""));
            assertTrue(Preprocessor.parse(env, "$difficultName = $a").
                    equals("someString = 42"));
            assertTrue(Preprocessor.parse(env, "$difficultName=$a").
                    equals("someString=42"));
            assertTrue(Preprocessor.parse(env, "'$difficultName=$a'").
                    equals("'$difficultName=$a'"));

        } catch (PreprocessException e) {
            assertTrue(false);
        }

        try {
            Preprocessor.parse(env, "abcd'a");
        } catch (PreprocessException e) {
            assertTrue(true);
        }
    }
}
