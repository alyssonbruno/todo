package repl;

import java.util.Arrays;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class CommandREPL {

    public static Integer terminal_repl() {
        try {
            Terminal terminal = TerminalBuilder.builder().system(true).build();
            LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

            Boolean repl = true;

            terminal.writer().println("Welcome to To-do Application");
            terminal
                .writer()
                .println("Enter help for assitence or quit to exit.");
            terminal.writer().flush();
            while (repl) {
                String[] commandString = lineReader.readLine(">>> ").split(" ");
                String fistOne = commandString[0];
                String[] othersStringsArray = Arrays.copyOfRange(
                    commandString,
                    1,
                    commandString.length
                );
                String othersStrings = String.join(" ", othersStringsArray);

                switch (fistOne) {
                    case "help":
                        terminal
                            .writer()
                            .println("add <strings> - add a new Task");
                        terminal
                            .writer()
                            .println(
                                "list [done|todo|doing] - list all Task or one of status (done, todo, doing)"
                            );
                        terminal
                            .writer()
                            .println(
                                "complete [task_id[] - mark the task (with task_id) as done. If isn´t task_id apply to all task in doing status."
                            );
                        terminal
                            .writer()
                            .println(
                                "start [task_id] - mark the task (with task_id) as doing. If isn´t task_id apply to all task in todo status."
                            );
                        terminal
                            .writer()
                            .println(
                                "remove <strings> - delete a task (with task_id). Task_id is mandatory"
                            );
                        terminal.writer().println("quit - exit from here");
                        break;
                    case "add":
                    case "append":
                        terminal.writer().println(othersStrings);
                        break;
                    case "remove":
                    case "delete":
                    case "del":
                        terminal.writer().println(othersStrings);
                        break;
                    case "list":
                        terminal.writer().println(othersStrings);
                        break;
                    case "complete":
                        terminal.writer().println(othersStrings);
                        break;
                    case "start":
                        terminal.writer().println(othersStrings);
                        break;
                    case "exit":
                    case "quit":
                        repl = false;
                        terminal.writer().println("Goodbye!");

                        break;
                }
                terminal.writer().flush();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
