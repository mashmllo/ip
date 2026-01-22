import java.util.Scanner;

/**
 * Handles user interactions.
 * <p>
 * This class is responsible for managing the main workflow of Sora,
 * including greeting the user, processing commands, and displaying farewell message.
 */
public class CommandHandler {

    private final Ui ui;
    private final Scanner scanner;
    private final TaskManager taskManager;
    private boolean isRunning;


    /**
     * Constructs a CommandHandler instance
     *
     * Initialized Ui, Scanner, TaskManager and signal that
     * the program is currently running. User are also
     * being greeted immediately upon the creation of the instance
     */
    public CommandHandler() {
        this.ui = new Ui();
        this.scanner = new Scanner(System.in);

        ui.greetUser();

        this.taskManager = new TaskManager();
        this.isRunning = true;

    }

    /**
     * Executes the command handler's main workflow
     * <p>
     * Serve as an entry point for handling user interactions or processing commands.
     */
    public void run() {

        while (isRunning) {
            String input = scanner.nextLine().strip();
            try {
                handleCommand(input);
            } catch (SoraException soraException) {
                Ui.showError(soraException.getMessage());
            }
        }

        ui.farewellMessage();
        scanner.close();
    }

    /**
     * Process the command entered by user
     *
     * @param input command entered by user
     */
    private void handleCommand(String input) {

        Command command = CommandParser.parse(input);
        if (command instanceof ExitCommand) {
            isRunning = false;
        }else {
            command.execute(taskManager, ui);
        }
    }
}
