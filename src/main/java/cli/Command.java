package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import repl.CommandREPL;

/**
 * Command is the main Class to terminal. This can call {@link repl.CommandREPL} when called without arguments.
 * @author Alysson
 * @version 2024-11
 */
public class Command {

    private static void readFromPipe() {
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

    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "--pipe":
                    readFromPipe();
                    break;
            }
            System.out.println("It's Work!");
            return;
        } else {
            CommandREPL.terminal_repl();
        }
    }
}
