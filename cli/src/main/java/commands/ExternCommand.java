package commands;

import core.Environment;

import java.io.*;

public class ExternCommand extends Command {
    private String command;

    public ExternCommand(String[] args) {
        super(args);
        command = "";
        for (String arg : args) {
            command += arg;
        }
    }

    @Override
    public InputStream run(Environment env, InputStream inputStream) throws CommandException {
        ProcessBuilder builder = new ProcessBuilder(command);
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
            throw new CommandException(command + ": IO error.");
        } catch (InterruptedException e) {
            throw new CommandException(command + ": interrupted.");
        }
    }
}
