/**
 * Thrown when user enters a command that does not follow a valid format
 */
public class InvalidFormatException extends SoraException {

    /**
     * Constructs an InvalidFormatException with an error message
     * @param message User-friendly error message
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
