package cli;

import repl.CommandREPL;

/**
 * Command is the main Class to terminal. This can call {@link repl.CommandREPL} when called without arguments.
 * @author Alysson
 * @version 2024-11
 */
public class Command {

    public static void main(String[] args) {
        if (args.length > 0) {
            for (String arg : args) {
                System.out.print(arg + ":");
            }
            System.out.println("It's Work!");
            return;
        }
        CommandREPL.terminal_repl();
    }
}
