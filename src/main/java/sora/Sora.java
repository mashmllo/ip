package sora;

import sora.manager.CommandHandler;
import sora.ui.OutputHandler;

/**
 * Entry point for the Sora task management chatbot.
 * <p>
 * This class initializes the {@link CommandHandler} and
 * starts the main interaction loop with the user.
 */
public class Sora {

    private final CommandHandler cmdHandler;

    /**
     * Constructs a Sora instance using a custom {@link OutputHandler}.
     *
     * @param outputHandler The output handler used to display messages
     */
    public Sora(OutputHandler outputHandler) {
        assert outputHandler != null : "OutputHandler should not be null";
        this.cmdHandler = new CommandHandler(outputHandler);
    }

    /**
     * Constructs a Sora instance for CLI usage.
     */
    public Sora() {
        this.cmdHandler = new CommandHandler();
    }

    /**
     * Processes user input and returns the response by Sora.
     *
     * @param input User input
     */
    public void processInput(String input) {
        cmdHandler.process(input);
    }


    /**
     * Main entry point for CLI usage.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Sora sora = new Sora();
        sora.cmdHandler.run();
    }
}
