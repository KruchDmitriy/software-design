package main;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class Tokenizer {
    private static final String SPLIT_CHARS = "\\s|\\b";
    private Tokenizer() {}
    public static List<String> parse(@NotNull final String processedLine) throws TokenizeException {
        List<String> literals = splitByQuotes(processedLine);
        List<String> tokens = new ArrayList<>();

        for (String literal : literals) {
            if (literal.charAt(0) != '\'' && literal.charAt(0) != '"') {
                String[] literalTokens = literal.split(SPLIT_CHARS);
                for (String token : literalTokens) {
                    if (!token.equals("")) {
                        tokens.add(token);
                    }
                }
            } else {
                tokens.add(literal.substring(1, literal.length() - 1));
            }
        }

        return tokens;
    }

    private static List<String> splitByQuotes(@NotNull String string) throws TokenizeException {
        List<String> literals = new ArrayList<>();

        String nonLiteral = "";
        for (int i = 0; i < string.length(); i++) {
            char curChar = string.charAt(i);
            if (curChar == '\'' || curChar == '"') {
                if (!nonLiteral.equals("")) {
                    literals.add(nonLiteral);
                    nonLiteral = "";
                }

                int endIndex = string.indexOf(curChar, i + 1);
                if (endIndex == -1) {
                    throw new TokenizeException("No matching second quote for " + curChar + ".");
                }

                literals.add(string.substring(i, endIndex + 1));
                i = endIndex;
            } else {
                nonLiteral += curChar;
            }
        }

        return literals;
    }
}
