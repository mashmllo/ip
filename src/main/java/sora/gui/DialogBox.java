package sora.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private static final String FXML_PATH = "/view/DialogBox.fxml";

    /**
     * Label displaying the dialog text
     */
    @FXML
    private Label dialog;

    /**
     * ImageView displaying the image
     */
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the given message and avatar image.
     *
     * @param message The message text to display
     * @param img     The image avatar
     * @throws NullPointerException If either {@code message} or {@code img} is
     *                              {@code null}. This exception is thrown to indicate
     *                              improper initialization of
     *                              the object.
     */
    public DialogBox(String message, Image img) throws NullPointerException {
        if (message == null) {
            throw new NullPointerException("Dialog message should not be null");
        }

        if (img == null) {
            throw new NullPointerException("Dialog image should not be null");
        }

        loadFxml();
        initializeContent(message, img);
    }

    /**
     * Loads FXML layout and binds it to this instance.
     */
    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                .getResource(FXML_PATH));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the Ui content.
     *
     * @param message The message text to display
     * @param img     The image avatar
     */
    private void initializeContent(String message, Image img) {
        this.dialog.setText(message);
        this.displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left
     * and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
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

