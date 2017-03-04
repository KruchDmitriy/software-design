package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that tokenizes string
 */
public final class Tokenizer {
    /**
     * We split string by whitespace symbols or bound of word, that
     * may contain '-' characters.
     */
    private static final String SPLIT_CHARS = "\\s|\\b&[^-]";
    private Tokenizer() {}

    /**
     * This function takes a processed line from preprocessor
     * and splits it on tokens. Token is a elementary part
     * of string, that may be interpreted later as a command.
     * @param processedLine
     * @return list of tokens
     * @throws TokenizeException - throws if mismatching quotes
     * were found
     */
    public static List<String> parse(final String processedLine)
            throws TokenizeException {
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

    /**
     * This method find single and double quotes in input string
     * and cut them to an individual tokens.
     * @param string - input string
     * @return list of string token in quotes or something that was between them
     * @throws TokenizeException
     */
    private static List<String> splitByQuotes(String string)
            throws TokenizeException {
        List<String> literals = new ArrayList<>();

        StringBuilder nonLiteral = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char curChar = string.charAt(i);
            if (curChar == '\'' || curChar == '"') {
                if (!(nonLiteral.length() == 0)) {
                    literals.add(nonLiteral.toString());
                    nonLiteral = new StringBuilder();
                }

                int endIndex = string.indexOf(curChar, i + 1);
                if (endIndex == -1) {
                    throw new TokenizeException("No matching second quote for "
                            + curChar + ".");
                }

                literals.add(string.substring(i, endIndex + 1));
                i = endIndex;
            } else {
                nonLiteral.append(curChar);
            }
        }

        if (nonLiteral.length() > 0) {
            literals.add(nonLiteral.toString());
        }

        return literals;
    }
}
