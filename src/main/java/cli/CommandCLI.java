package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import repl.CommandREPL;

/**
 * Command is the main Class to terminal. This can call {@link repl.CommandREPL} when called without arguments.
 * @author Alysson
 * @version 2024-11
 */
@Command(
    name = "todo",
    mixinStandardHelpOptions = true,
    version = "2024-11",
    description = "A simple to-do task manager for cli and repl"
)
public class CommandCLI implements Callable<Integer> {

    @Option(
        names = { "-p", "--pipe" },
        description = "a pipe will send to todo program"
    )
    Boolean isPipe = false;

    private void readFromPipe() {
        try {
            if (System.in.available() > 0) {
                BufferedReader pipeReader = new BufferedReader(
                    new InputStreamReader(System.in)
                );
                String line;
                while ((line = pipeReader.readLine()) != null) {
                    System.out.printf("add %s\n", line);
                }
            } else {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {
        if (isPipe) {
            readFromPipe();
            return 0;
        }
        return (CommandREPL.terminal_repl());
    }

    public static void main(String[] args) {
        Integer exitCode;
        exitCode = new CommandLine(new CommandCLI()).execute(args);
        System.exit(exitCode);
    }
}
