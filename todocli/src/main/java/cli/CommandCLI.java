package cli;

import java.io.File;

import cc.ab78.web.TodoWebApplication;
import cli.domain.FileFormat;
import cli.domain.TaskStatus;
import cli.domain.Task;
import cli.repl.CommandREPL;
import cli.service.CodeAnaliseService;
import cli.service.SystemService;
import cli.service.TaskService;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParseResult;

/**
 * Command is the main Class to terminal. This can call {@link cli.repl.CommandREPL} when called without arguments.
 * @author Alysson
 * @version 2024-11
 */
@Command(
    name = "todo",
    mixinStandardHelpOptions = true,
    version = "2024-11",
    description = "A simple to-do task manager for cli and repl"
)
public class CommandCLI {

    @Parameters(index = "0..*", hidden = true)
    String[] taskMessage;

    @ArgGroup(exclusive = true, multiplicity = "0..1")
    InputStream inputFile;

    @ArgGroup(exclusive = true, multiplicity = "0..1")
    Operations op;

    @Option(
        names = { "-w", "--web" },
        defaultValue = "false",
        description = "Start Webservice. Will ignore all others options"
    )
    Boolean startWeb = false;

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
            defaultValue = "${FileFormat.TEXT}",
            description = "Should be @|italic,bold TEXT|@ or @|italic,bold CODE|@. @|italic,bold TEXT|@ (default) create a task for all full line, @|italic,bold CODE|@ create only lines with tags",
            required = false
        )
        FileFormat format = FileFormat.TEXT;

        @Option(
            names = { "--file-tags" },
            defaultValue = "todo",
            description = "Use with @|italic,bold --file-format=|@@|italic,bold,fg(green) CODE|@, include tags separeted by coma (,), in the file these tags need to have a colon (:) after it.",
            required = false
        )
        String tags = "todo";
    }

    TaskService taskService = new TaskService();

    SystemService systemService = new SystemService();

    CodeAnaliseService codeAnaliseService = new CodeAnaliseService();

    /*
        todo: how to make test?
        todo: implements save do file database
    */
    private void startWebApp() {
        TodoWebApplication.start();
    }

    private void addTask(String taskDescrition, String[] taskMessage) {
        Long id = taskService.create(
            taskDescrition,
            taskMessage != null ? String.join(" ", taskMessage) : ""
        );
        System.out.printf("Taks %d added!", id);
    }

    private void listTask(TaskStatus status){
        if(status!=null){
            for (Task t : taskService.list(status) ){
                System.out.printf("%d - %s", t.getId(), t.getTitle());
            }
        }
    }

    public Integer call() throws Exception {
        if (startWeb) {
            startWebApp();
            return 0;
        }

        if (inputFile != null) {
            if (inputFile.isPipe) {
                codeAnaliseService.fromBufferedReader(
                    systemService.readFromFile(null),
                    null,
                    null
                );
            } else if (
                inputFile.fileOperations != null &&
                inputFile.fileOperations.file != null
            ) {
                if (inputFile.fileOperations.configFile != null) {
                    codeAnaliseService.fromBufferedReader(
                        systemService.readFromFile(
                            inputFile.fileOperations.file
                        ),
                        inputFile.fileOperations.configFile.format,
                        inputFile.fileOperations.configFile.tags.split(",")
                    );
                } else {
                    codeAnaliseService.fromBufferedReader(
                        systemService.readFromFile(
                            inputFile.fileOperations.file
                        ),
                        FileFormat.TEXT,
                        null
                    );
                }
            } else {
                throw new Exception("Invalid Operations");
            }
        } else if (op != null) {
            if (op.taskDescrition != null) {
                addTask(op.taskDescrition, taskMessage);
                return 0;
            } else if (op.statusToList != null ){
                listTask(op.statusToList);
            } else if(
                    op.completeTaskId != null ||
                op.deleteTaskId != null ||
                op.startTaskId != null
            ) {
                System.out.println(String.join(" ", taskMessage));
                System.out.println(op);
            }
        } else {
            return CommandREPL.terminal_repl();
        }
        return 0;
    }

    public static Integer callable(CommandCLI commandCLI) {
        try {
            return commandCLI.call();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static void main(String[] args) {
        Integer exitCode = 0;
        CommandCLI commandCLI = new CommandCLI();
        ParseResult parseResult = new CommandLine(commandCLI).parseArgs(args);
        try {
            if (!CommandLine.printHelpIfRequested(parseResult)) {
                exitCode = callable(commandCLI);
            }
        } catch (ParameterException ex) { // command line arguments could not be parsed
            System.err.println(ex.getMessage());
            ex.getCommandLine().usage(System.err);
            exitCode = 2;
        }

        System.exit(exitCode);
    }
}
