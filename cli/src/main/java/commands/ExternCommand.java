package commands;

import core.Environment;

import java.io.*;

public class ExternCommand extends Command {
    private String[] args;

    public ExternCommand(String[] args) {
        super(args);
        this.args = args;
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        ProcessBuilder builder = new ProcessBuilder(args);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            OutputStream stdin = process.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(inputStream));

            String line;
            while ((line = inputReader.readLine()) != null) {
                writer.write(line + "\n");
                writer.flush();
            }

            process.waitFor();
            return process.getInputStream();
        } catch (IOException e) {
            throw new CommandException(args[0] + ": IO error.");
        } catch (InterruptedException e) {
            throw new CommandException(args[0] + ": interrupted.");
        }
    }
}
