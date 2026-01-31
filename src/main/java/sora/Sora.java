package sora;

import sora.manager.CommandHandler;

/**
 * Entry point for the Sora task management chatbot.
 * <p>
 * This class initializes the {@link CommandHandler} and
 * starts the main interaction loop with the user.
 */
public class Sora {
    /**
     * Generates response for user chat message
     * @param input User input
     * @return the user input for echoing
     */
    public String processInput(String input) {
        return input;
    }
    public static void main(String[] args) {
        Sora sora = new Sora();
        sora.cmdHandler.run();
    }
}
