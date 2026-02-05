package sora.ui;

import static sora.gui.MainWindow.SORA_IMG;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    private boolean isIconAdded = false;

    /**
     * Constructs a {@link GuiOutput} instance.
     *
     * @param container  The {@link VBox} where messages will be displayed.
     */
    public GuiOutput(VBox container) {
        this.container = container;
    }

    @Override
    public void show(String message) {
        Platform.runLater(() -> {
            if (!isIconAdded) {
                container.getChildren().add(
                        DialogBox.getSoraDialog("", SORA_IMG)
                );
                isIconAdded = true;
            }
            Label msg = new Label(message);
            msg.setWrapText(true);
            msg.setMaxWidth(Double.MAX_VALUE);
            msg.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 5px; -fx-background-radius: 5px");
            container.getChildren().add(msg);
        });
        isIconAdded = false;
    }
}
