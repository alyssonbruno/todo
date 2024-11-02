package cli;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class CommandCLI {

    public static void main(String[] args) {
        try {
            Terminal terminal = TerminalBuilder.builder().system(true).build();
            LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

            Boolean repl = true;

            terminal
                .writer()
                .printf(
                    "Welcome to To-do Aplication\nEnter a Command or help for assitence\n\n"
                );
            terminal.writer().flush();
            while (repl) {
                String comandString = lineReader.readLine(">>> ");
                switch (comandString) {
                    case "exit":
                    case "quit":
                        repl = false;
                        terminal.writer().println("Goodbye!");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
