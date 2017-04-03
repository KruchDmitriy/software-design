package ru.spbau.mit;

import org.junit.Test;
import ru.spbau.mit.core.Tokenizer;
import ru.spbau.mit.core.exceptions.TokenizeException;

import java.util.List;

import static junitx.framework.Assert.assertEquals;

public class TokenizerTest {
    @Test
    public void simpleTest() throws TokenizeException {
        List<String> tokens = Tokenizer.parse(
                "\"i'm sittin'\" 'i\"m sleepin\"'");
        assertEquals("i'm sittin'", tokens.get(0));
        assertEquals("i\"m sleepin\"", tokens.get(1));
        assertEquals(2, tokens.size());

        tokens = Tokenizer.parse("cat -my 'cat'");
        assertEquals(3, tokens.size());
        assertEquals("cat", tokens.get(0));
        assertEquals("-my", tokens.get(1));
        assertEquals("cat", tokens.get(2));

        tokens = Tokenizer.parse("xy2Abin=15\t|\tmug=\"1 2\"  | y2 = 27 | "
                + "echo \"hello\"");
        String[] answer = {
                "xy2Abin=15", "|", "mug=", "1 2", "|", "y2", "=", "27", "|",
                "echo", "hello"
        };
        assertEquals(answer.length, tokens.size());
        for (int i = 0; i < answer.length; i++) {
            assertEquals(answer[i], tokens.get(i));
        }
    }

    @Test(expected = TokenizeException.class)
    public void exceptionTest() throws TokenizeException {
        Tokenizer.parse("\"i'm sittin im sleepin");
    }

    @Test
    public void pwdTest() throws TokenizeException {
        List<String> tokens = Tokenizer.parse("pwd");
        assertEquals("pwd", tokens.get(0));
    }
}

