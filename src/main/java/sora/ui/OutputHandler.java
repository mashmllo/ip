package sora.ui;

import sora.exception.InvalidFormatException;

/**
 * Output destination interface.
 * <p>
 * Can be implemented for CLI, GUI or other destination.
 */
public interface OutputHandler {
    /**
     * Sends a message to the output destination.
     *
     * @param message The message to be displayed.
     * @throws InvalidFormatException If {@code message} is {@code null}. This exception
     *                              is thrown to indicate improper initialization of the
     *                              object.
     */
    void show(String message)
            throws InvalidFormatException;

    /**
     * Sends an error message to the output destination.
     *
     * @param message The error message to be displayed; must not be {@code null}.
     * @throws InvalidFormatException if {@code message} is {@code null},
     *                                indicating that the message is improperly initialized.
     */
    void showError(String message)
            throws InvalidFormatException;
}
