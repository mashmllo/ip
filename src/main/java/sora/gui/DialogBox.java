package sora.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a single dialog box containing a message and an image.
 * <p>
 * Used in Sora GUI to display messages from user and Sora output.
 * Each dialog box contains a {@link Label} for the message text and an {@link ImageView}.
 * Dialog boxes can be aligned differently for user and Sora messages
 */
public class DialogBox extends HBox {

    /**
     * Label displaying the dialog text
     */
    private Label text;

    /**
     * ImageView displaying the image
     */
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the given message and avatar image.
     *
     * @param message The message text to display
     * @param img     The image avatar
     */
    public DialogBox(String message, Image img) {
        text = new Label(message);
        displayPicture = new ImageView(img);

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }
}

