package sora.exception;

/**
 * Base class for all exception specific to Sora.
 * <p>
 * Serves as the parent for all custom exceptions used in Sora.
 * Being abstract ensures that errors thrown are explicit and meaningful
 * to the user.
 */
public abstract class SoraException extends RuntimeException {

    /**
     * Construct {@code SoraException} with a user-friendly error
     * message.
     *
     * @param message The error message to be displayed to the user.
     */
    public SoraException(String message) {
        super(message);
    }

}
