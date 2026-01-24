package sora.exception;

/**
 * Thrown when user enters a command that does not follow a valid format.
 */
public class InvalidFormatException extends SoraException {

    /**
     * Constructs a {@code InvalidFormatException} with a user-friendly
     * error message.
     *
     * @param message The error message to be displayed to the user.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
