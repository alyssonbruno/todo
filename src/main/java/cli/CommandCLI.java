package cli;

import domain.TaskStatus;
import java.io.File;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
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

    @Parameters(index = "0..*", hidden = true)
    String[] taskMessage;

    @ArgGroup(exclusive = true, multiplicity = "0..1")
    InputStream inputFile;

    @ArgGroup(exclusive = true, multiplicity = "0..1")
    Operations op;

    static class Operations {

        @Option(
            names = { "-a", "--add" },
            description = "add a new task",
            required = true
        )
        String taskDescrition = null;

        @Option(
            names = { "-l", "--list" },
            description = "list task",
            defaultValue = "${TaskStatus.TODO}",
            required = true
        )
        TaskStatus statusToList = null;

        @Option(
            names = { "-c", "--complete" },
            description = "Chage a Task to DONE",
            required = true
        )
        Long completeTaskId = null;

        @Option(
            names = { "-s", "--start" },
            description = "Chage a Task to DOING",
            required = true
        )
        Long startTaskId = null;

        @Option(
            names = { "-d", "--delete" },
            description = "Delete a task",
            required = true
        )
        Long deleteTaskId = null;
    }

    static class InputStream {

        @Option(
            names = { "-p", "--pipe" },
            description = "a pipe will send to todo program",
            required = true
        )
        Boolean isPipe = false;

        @ArgGroup(exclusive = false, multiplicity = "0..1")
        FileOperations fileOperations;
    }

    static class FileOperations {

        @Option(
            names = { "-f", "--file" },
            description = "a file with tasks to add",
            required = true
        )
        File file = null;

        @ArgGroup(exclusive = false, multiplicity = "0..1")
        OptionsFileConfigs configFile;
    }

    static class OptionsFileConfigs {

        @Option(
            names = { "--file-format" },
            defaultValue = "text",
            description = "Should be text or code. Text (default) create a task for all full line, code create only lines with tags",
            required = true
        )
        String format = "text";

        @Option(
            names = { "--file-tags" },
            defaultValue = "todo",
            description = "Use with --file-format=code, include tags separeted by coma (,), these tags need to have a : after it.",
            required = true
        )
        String tags = "todo";
    }

    /*
        todo: how to make test?
        todo: implements save do file database
    */

    @Override
    public Integer call() throws Exception {
        if (inputFile != null) {
            if (inputFile.isPipe) {
                CodeAnaliseService.fromBufferedReader(
                    SystemService.readFromFile(null),
                    null,
                    null
                );
            } else if (
                inputFile.fileOperations != null &&
                inputFile.fileOperations.file != null
            ) {
                CodeAnaliseService.fromBufferedReader(
                    SystemService.readFromFile(inputFile.fileOperations.file),
                    inputFile.fileOperations.configFile.format,
                    inputFile.fileOperations.configFile.tags.split(",")
                );
            } else {
                throw new Exception("Invalid Operations");
            }
        } else if (op != null) {
            if (
                op.taskDescrition != null ||
                op.completeTaskId != null ||
                op.deleteTaskId != null ||
                op.statusToList != null ||
                op.startTaskId != null
            ) {
                System.out.println(String.join(" ", taskMessage));
                System.out.println(op);
            }
        } else {
            return (CommandREPL.terminal_repl());
        }
        return 0;
    }

    public static void main(String[] args) {
        Integer exitCode;
        exitCode = new CommandLine(new CommandCLI()).execute(args);
        System.exit(exitCode);
    }
}
