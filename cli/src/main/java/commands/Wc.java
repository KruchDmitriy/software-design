package commands;

import core.Environment;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Wc extends Command {
    private final String[] filenames;

    public Wc(String[] args) {
        super(args);
        filenames = args;
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        String answer = "";
        if (filenames == null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            answer = getCounts(reader);
        } else {
            for (String filename : filenames) {
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(filename));
                } catch (FileNotFoundException e) {
                    throw new CommandException("wc: No such file or directory.");
                }

                answer += getCounts(reader) + " " + filename + "\n";
            }
        }

        return new ByteArrayInputStream(answer.getBytes(StandardCharsets.UTF_8));
    }

    public String getCounts(BufferedReader reader) throws CommandException {
        try {
            String line;
            int countBytes = 0;
            int countWords = 0;
            int countLines = 0;
            while ((line = reader.readLine()) != null) {
                countBytes += line.getBytes("UTF-8").length;
                countWords += countNonEmpty(line.split("\\s+"));
                countLines++;
            }
            reader.close();

            return countLines + "\t" + countWords + "\t" + countBytes;
        } catch (IOException e) {
            throw new CommandException("wc: Error while reading stream.");
        }
    }

    private int countNonEmpty(String[] strings) {
        int count = 0;
        for (String str : strings) {
            if (str.length() != 0) {
                count++;
            }
        }
        return count;
    }
}
