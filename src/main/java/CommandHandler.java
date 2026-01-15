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
        ui.farewellMessage();
    }
}
