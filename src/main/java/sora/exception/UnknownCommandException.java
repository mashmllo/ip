package sora.exception;

/**
 * Thrown when user enters an unsupported command
 */
public class UnknownCommandException extends SoraException {

    /**
     * Constructs a UnknownCommandException with a specific error message
     */
    public UnknownCommandException() {
        super("Oops! I didn't recognize that command"
                + "\n Please enter a valid command");
    }
}
