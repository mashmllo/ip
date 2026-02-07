package sora.manager;

import java.util.Scanner;

import sora.command.Command;
import sora.command.ExitCommand;
import sora.exception.SoraException;
import sora.parser.CommandParser;
import sora.ui.OutputHandler;
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
     * Constructs a {@code CommandHandler} instance for GUI or custom output.
     * <p>
     * Initializes the {@code Ui} and {@code TaskManager}
     * and signal that the program is currently running.
     *
     * @param outputHandler The {@link OutputHandler} to use for rendering messages.
     */
    public CommandHandler(OutputHandler outputHandler) {
        assert outputHandler != null : "OutputHandler must not be null";
        this.ui = new Ui(outputHandler);
        this.scanner = null; // GUI does not use scanner
        this.taskManager = new TaskManager(outputHandler);
        this.isRunning = true;

        this.ui.guiGreetUser();
    }

    /**
     * Executes the main workflow of the command handler for CLI usage.
     * <p>
     * Serve as an entry point for handling user interactions and processing commands.
     * Continues running until an {@code ExitCommand} is entered by the user.
     */
    public void run() {
        assert this.scanner != null : "Scanner object must be initialize";

        while (this.isRunning) {
            String input = this.scanner.nextLine().strip();
            try {
                handleCommand(input);
            } catch (SoraException soraException) {
                this.ui.showError(soraException.getMessage());
            }
        }
        this.scanner.close();
    }

    /**
     * Processes a single input command for GUI usage.
     *
     * @param input The command input string from the user
     */
    public void process(String input) {
        assert input != null : "Input string should not be null";

        try {
            handleCommand(input);
        } catch (SoraException soraException) {
            this.ui.showError(soraException.getMessage());
        }
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
            this.ui.farewellMessage();
        } else {
            command.execute(this.taskManager, this.ui);
        }
    }
}
