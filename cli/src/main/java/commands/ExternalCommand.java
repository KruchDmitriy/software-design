package commands;

import core.Environment;

import java.io.*;

/**
 * Represent a command that will be executed as a standalone process.
 */
public class ExternalCommand extends Command {
    private String[] args;

    public ExternalCommand(String[] args) {
        super(args);
        this.args = args;
    }

    /**
     * Run a command in process. If no arguments passed,
     * it would read from stdin.
     * @param env
     * @param inputStream
     * @return Output of a process.
     * @throws CommandException if there were no command or problems with IO,
     * or it was interrupted.
     */
    @Override
    public InputStream run(Environment env, InputStream inputStream)
            throws CommandException {
        ProcessBuilder builder = new ProcessBuilder(args);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();

            if (args.length == 1) {
                OutputStream stdin = process.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new
                        OutputStreamWriter(stdin));

                BufferedReader inputReader = new BufferedReader(
                        new InputStreamReader(inputStream));

                String line;
                while ((line = inputReader.readLine()) != null) {
                    writer.write(line + "\n");
                }
            }

            process.waitFor();
            return process.getInputStream();
        } catch (IOException e) {
            throw new CommandException(args[0] + ": IO error."
                    + e.getMessage());
        } catch (InterruptedException e) {
            throw new CommandException(args[0] + ": interrupted.");
        }
    }
}
