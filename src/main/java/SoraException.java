/**
 * Base class for all exception specific to Sora
 * <p>
 * This class serves as the parent for all custom exceptions used in Sora.
 * It is an abstract class to ensure that the error thrown is explicit and meaningful
 * to the user.
 * <p>
 */
public abstract class SoraException extends RuntimeException {

    /**
     * Construct SoraException with an error message
     * @param message User-friendly error message
     */
    public SoraException(String message) {
        super(message);
    }


}
