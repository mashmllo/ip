package sora.manager;

import java.util.Scanner;

import sora.command.Command;
import sora.command.ExitCommand;
import sora.exception.SoraException;
import sora.parser.CommandParser;
import sora.ui.Ui;

/**
 * Represents a handler for user interactions in Sora.
 * <p>
 * Responsible for managing the main workflow of Sora,
 * including greeting the user, processing commands, and displaying farewell message.
 */
public class CommandHandler {

    private final Ui ui;
    private final Scanner scanner;
    private final TaskManager taskManager;
    private boolean isRunning;


    /**
     * Constructs a {@code CommandHandler} instance.
     * <p>
     * Initializes the {@code Ui}, {@code Scanner}, and {@code TaskManager}
     * and signal that the program is currently running.
     * The user is greeted immediately upon the creation of the instance.
     */
    public CommandHandler() {
        this.ui = new Ui();
        this.scanner = new Scanner(System.in);
        this.taskManager = new TaskManager();
        this.isRunning = true;

        this.ui.greetUser();
    }

    /**
     * Executes the main workflow of the command handler.
     * <p>
     * Serve as an entry point for handling user interactions and processing commands.
     * Continues running until an {@code ExitCommand} is entered by the user.
     */
    public void run() {

        while (this.isRunning) {
            String input = this.scanner.nextLine().strip();
            try {
                handleCommand(input);
            } catch (SoraException soraException) {
                Ui.showError(soraException.getMessage());
            }
        }

        this.ui.farewellMessage();
        this.scanner.close();
    }

    /**
     * Processes the command entered by user.
     *
     * @param input The command entered by the user.
     */
    private void handleCommand(String input) {
        Command command = CommandParser.parse(input);
        if (command instanceof ExitCommand) {
            this.isRunning = false;
        } else {
            command.execute(this.taskManager, this.ui);
        }
    }
}
