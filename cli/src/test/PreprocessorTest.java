package test;

import main.Environment;
import main.PreprocessException;
import main.Preprocessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PreprocessorTest {
    @Test
    public void testSimple() {
        Environment env = new Environment();
        env.write("a", "42");
        env.write("difficultName", "someString");
        try {
            assertEquals("42", Preprocessor.parse(env, "$a"));
            assertEquals("42 + 1", Preprocessor.parse(env, "$a + 1"));
            assertEquals("", Preprocessor.parse(env, "$b"));
            assertEquals("'$b'", Preprocessor.parse(env, "'$b'"));
            assertEquals("\"42\"", Preprocessor.parse(env, "\"$a\""));
            assertEquals("someString = 42", Preprocessor.parse(env, "$difficultName = $a"));
            assertEquals("someString=42", Preprocessor.parse(env, "$difficultName=$a"));
            assertEquals("'$difficultName=$a'", Preprocessor.parse(env, "'$difficultName=$a'"));

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
