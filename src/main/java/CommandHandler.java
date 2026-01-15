/**
 * Handles user interactions.
 * <p>
 * This class is responsible for managing the main workflow of Sora,
 * including greeting the user, processing commands, and displaying farewell message.
 */
public class CommandHandler {

    private final Ui ui;

    public CommandHandler() {
        this.ui = new Ui();
    }

    /**
     * Executes the command handler's main workflow
     * <p>
     * Serve as an entry point for handling user interactions or processing commands.
     */
    public void run() {
        ui.greetUser();
        ui.echo("test");
        ui.farewellMessage();
    }
}
