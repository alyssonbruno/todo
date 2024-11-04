package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
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

    @Option(
        names = { "-f", "--file" },
        description = "a file with tasks to add"
    )
    File file = null;

    @Option(
        names = { "--file-format" },
        description = "Should be text or code. Text (default) create a task for all full line, code create only lines with 'todo:'"
    )
    String format = "text";

    /*  todo: others options: add, list, start, complete, delete
        todo: how to make test
        todo: implements save do file database
    */
    /** Receive a stream of information from PIPE then add one taks
     *  for each line from it. If --file-format is text (default)
     *  all line will be include, if is code only line with "todo:"
     *  will be added.
     * @author Alysson
     * @version 2024-11
     */
    private void readFromPipe() {
        try {
            BufferedReader pipeReader;
            String line;

            if (file != null) {
                pipeReader = Files.newBufferedReader(file.toPath());
            } else {
                pipeReader = new BufferedReader(
                    new InputStreamReader(System.in)
                );
            }

            while ((line = pipeReader.readLine()) != null) {
                if (format.equals("code")) {
                    if (line.contains("todo:")) {
                        int index = line.indexOf("todo:");
                        System.out.printf(
                            "add %s\n",
                            line.substring(index + 5)
                        );
                    }
                } else {
                    System.out.printf("add %s\n", line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {
        if (isPipe || file != null) {
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
