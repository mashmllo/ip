package sora;

import sora.manager.CommandHandler;

/**
 * Entry point for the Sora task management chatbot.
 * <p>
 * This class initializes the {@link CommandHandler} and
 * starts the main interaction loop with the user.
 */
public class Sora {
    public static void main(String[] args) {
        //Initialize the command handler and start Sora
        CommandHandler cmdHandler = new CommandHandler();
        cmdHandler.run();
    }

}
