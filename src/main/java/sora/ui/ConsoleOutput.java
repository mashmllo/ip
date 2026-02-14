package sora.ui;

import sora.exception.InvalidFormatException;

/**
 * Implementation of {@link OutputHandler} for CLI output.
 * <p>
 * Messages sent via {@link #show(String)} will be printed to the console
 */
public class ConsoleOutput implements OutputHandler {

    private static final String DIVIDER =
            ". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .";
    /**
     * Prints the given message to the console.
     *
     * @param message The message to be displayed
     * @throws InvalidFormatException If {@code message} is {@code null}. This exception
     *                              is thrown to indicate improper initialization of the
     *                              object.
     */
    @Override
    public void show(String message) {
        if (message == null) {
            throw new InvalidFormatException("Message should not be null");
        }

        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Prints the horizontal divider for readability.
     */
    public void printLine() {
        System.out.println(DIVIDER);
    }
}
