package ru.spbau.mit.core;

import ru.spbau.mit.core.exceptions.PreprocessorException;

/**
 * Contains methods that preprocess a string
 */
public final class Preprocessor {
    private Preprocessor() {}

    /**
     * Takes a raw string from shell and substitute all occurrences
     * of $var, if $var not in single quotes, to var from environment.
     * @param environment
     * @param rawLine
     * @return String where made all substitutions
     * @throws PreprocessorException - throws if mismatching quotes were found
     */
    public static String parse(Environment environment, final String rawLine)
            throws PreprocessorException {
        StringBuilder processedLineBuilder = new StringBuilder();
        for (int i = 0; i < rawLine.length(); i++) {
            char curChar = rawLine.charAt(i);
            if (curChar == '\'') {
                int j = rawLine.indexOf('\'', i + 1);
                if (j == -1) {
                    throw new PreprocessorException("Only one single quote.");
                }
                processedLineBuilder.append(rawLine.substring(i, j + 1));
                i = j;
            } else if (curChar == '$') {
                int endNamePosition = getFirstNonAlpha(rawLine, i + 1);
                String name = rawLine.substring(i + 1, endNamePosition);
                String value = environment.read(name);
                processedLineBuilder.append(value);
                i = endNamePosition - 1;
            } else {
                processedLineBuilder.append(curChar);
            }
        }
        return processedLineBuilder.toString();
    }

    private static int getFirstNonAlpha(String string, int fromIndex) {
        if (fromIndex < 0) {
            fromIndex = 0;
        } else if (fromIndex > string.length()) {
            return -1;
        }

        for (int i = fromIndex; i < string.length(); i++) {
            if (!Character.isAlphabetic(string.charAt(i))) {
                return i;
            }
        }

        return string.length();
    }
}

