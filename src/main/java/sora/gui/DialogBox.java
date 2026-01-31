package sora.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String message, Image img) {
        return new DialogBox(message, img);
    }

    public static DialogBox getSoraDialog(String message, Image img) {
        var db = new DialogBox(message, img);
        db.flip();
        return db;
    }
}

