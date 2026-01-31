package sora.ui;

/**
 * Output destination interface.
 * <p>
 * Can be implemented for CLI, GUI or other destination.
 */
public interface OutputHandler {
    /**
     * Sends a message to the output destination
     *
     * @param message The message to be displayed
     */
    void show(String message);
}
