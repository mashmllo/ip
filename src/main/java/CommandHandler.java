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
    private boolean isRunning;

    public CommandHandler() {
        this.ui = new Ui();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    /**
     * Executes the command handler's main workflow
     * <p>
     * Serve as an entry point for handling user interactions or processing commands.
     */
    public void run() {
        ui.greetUser();


        while (isRunning) {
            String cmd = scanner.nextLine().strip();
            handleCommand(cmd);
        }

        ui.farewellMessage();
        scanner.close();
    }


    /**
     * Process the command entered by user
     *
     * @param cmd command entered by user
     */
    private void handleCommand(String cmd) {
        if (cmd.equalsIgnoreCase("bye")) {
            isRunning = false;
        } else {
            ui.echo(cmd);
        }
    }
}
