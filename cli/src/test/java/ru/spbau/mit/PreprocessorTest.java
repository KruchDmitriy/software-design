package ru.spbau.mit;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.core.Environment;
import ru.spbau.mit.core.Preprocessor;
import ru.spbau.mit.core.exceptions.PreprocessorException;

import static junitx.framework.Assert.assertEquals;

public class PreprocessorTest {
    private Environment env;

    @Before
    public void setUp() {
        env = new Environment();
        env.write("a", "42");
        env.write("difficultName", "someString");
    }

    @Test
    public void testSimple() throws PreprocessorException {
        assertEquals("42", Preprocessor.parse(env, "$a"));
        assertEquals("42 + 1", Preprocessor.parse(env,
                "$a + 1"));
        assertEquals("", Preprocessor.parse(env, "$b"));
        assertEquals("'$b'", Preprocessor.parse(env, "'$b'"));
        assertEquals("\"42\"",
                Preprocessor.parse(env, "\"$a\""));
        assertEquals("someString = 42",
                Preprocessor.parse(env, "$difficultName = $a"));
        assertEquals("someString=42",
                Preprocessor.parse(env, "$difficultName=$a"));
        assertEquals("'$difficultName=$a'",
                Preprocessor.parse(env, "'$difficultName=$a'"));
    }

    @Test(expected = PreprocessorException.class)
    public void exceptionTest() throws PreprocessorException {
        Preprocessor.parse(env, "abcd'a");
    }
}
