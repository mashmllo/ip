package sora.ui;

import static sora.gui.MainWindow.SORA_IMG;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sora.exception.InvalidFormatException;
import sora.gui.DialogBox;

/**
 * Implementation of {@link OutputHandler} for JavaFx GUI output.
 * <p>
 * Messages sent via {@link #show(String)} will be added as {@link Label} elements
 * to a provided {@link VBox} container. Uses {@link Platform#runLater(Runnable)}
 * to ensure thread safety on the JavaFx Application Thread.
 */
public class GuiOutput implements OutputHandler {

    private final VBox container;

    /**
     * Constructs a {@link GuiOutput} instance.
     *
     * @param container  The {@link VBox} where messages will be displayed.
     */
    public GuiOutput(VBox container) {
        assert container != null : "VBox must not be null";
        this.container = container;
    }

    @Override
    public void show(String message) {
        if (message == null) {
            throw new InvalidFormatException("Message should not be null");
        }
        Platform.runLater(() ->
            this.container.getChildren().add(
                    DialogBox.getSoraDialog(message, SORA_IMG)
            )
        );
    }

    @Override
    public void showError(String message) throws InvalidFormatException {
        if (message == null) {
            throw new InvalidFormatException("Message should not be null");
        }

        Platform.runLater(() ->
            this.container.getChildren().add(
                    DialogBox.getErrorDialog(message, SORA_IMG)
            )
        );
    }
}
