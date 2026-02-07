package sora.ui;

/**
 * Implementation of {@link OutputHandler} for CLI output.
 * <p>
 * Messages sent via {@link #show(String)} will be printed to the console
 */
public class ConsoleOutput implements OutputHandler {

    /**
     * Prints the given message to the console.
     *
     * @param message The message to be displayed
     */
    @Override
    public void show(String message) {
        assert message != null : "Message should not be null";

        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Prints the horizontal divider for readability.
     */
    public void printLine() {
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }
}
