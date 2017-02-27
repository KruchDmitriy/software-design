package main;

public final class Preprocessor {
    private Preprocessor() {}
    public static String parse(Environment environment, final String rawLine) throws PreprocessException {
        String processedLine = "";
        for (int i = 0; i < rawLine.length(); i++) {
            char curChar = rawLine.charAt(i);
            if (curChar == '\'') {
                int j = rawLine.indexOf('\'', i + 1);
                if (j == -1) {
                    throw new PreprocessException("Only one single quote.");
                }
                processedLine += rawLine.substring(i, j + 1);
                i = j;
            } else if (curChar == '$') {
                int endNamePosition = getFirstNonAlpha(rawLine, i + 1);
                String name = rawLine.substring(i + 1, endNamePosition);
                String value = environment.read(name);
                processedLine += value;
                i = endNamePosition - 1;
            } else {
                processedLine += curChar;
            }
        }
        return processedLine;
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

