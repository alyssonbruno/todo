package cli;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import repl.CommandREPL;
import service.CodeAnaliseService;
import service.SystemService;

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
        description = "Should be text or code. Text (default) create a task for all full line, code create only lines with tags"
    )
    String format = "text";

    @Option(
        names = { "--tags", "-t" },
        description = "Use with --file-format=code, include tags separeted by coma (,), these tags need to have a : after it."
    )
    ArrayList<String> tags = new ArrayList<>();

    /*  todo: others options: add, list, start, complete, delete
        todo: how to make test
        todo: implements save do file database
    */

    public CommandCLI() {
        if (tags.isEmpty()) {
            tags.add("todo");
        }
    }

    @Override
    public Integer call() throws Exception {
        if (isPipe || (file != null)) {
            CodeAnaliseService.fromBufferedReader(
                SystemService.readFromFile(file),
                format,
                tags
            );
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
