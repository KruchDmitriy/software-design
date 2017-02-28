package test;

import main.TokenizeException;
import main.Tokenizer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenizerTest {
    @Test
    public void simpleTest() {
        try {
            List<String> tokens = Tokenizer.parse("\"i'm sittin'\" 'i\"m sleepin\"'");
            assertEquals("i'm sittin'", tokens.get(0));
            assertEquals("i\"m sleepin\"", tokens.get(1));
            assertEquals(2, tokens.size());

            tokens = Tokenizer.parse("cat my 'cat'");
            assertEquals(3, tokens.size());
            assertEquals("cat", tokens.get(0));
            assertEquals("my", tokens.get(1));
            assertEquals("cat", tokens.get(2));

            tokens = Tokenizer.parse("xy2Abin=15 | y1=28 | y2 = 27 | echo \"hello\"");
            String[] answer = {
              "xy2Abin", "=", "15", "|", "y1", "=", "28",
                    "|", "y2", "=", "27", "|", "echo", "hello"
            };
            assertEquals(answer.length, tokens.size());
            for (int i = 0; i < answer.length; i++) {
                assertEquals(answer[i], tokens.get(i));
            }

        } catch (TokenizeException e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }
}

