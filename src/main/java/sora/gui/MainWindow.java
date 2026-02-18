package sora.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import sora.Sora;
import sora.ui.GuiOutput;

/**
 * Controller for the main GUI window of the SOra application.
 */
public class MainWindow extends AnchorPane {

    /**
     * Image representing the user avatar in the GUI.
     * <p>
     * Loaded from resource path {@code /images/user.png}.
     * <p>
     * Image source: <a href="https://uxwing.com/users-icon/">User icon</a>
     * License: All icons on this site can be used for personal, commercial,
     *          and client projects without attribution.
     */
    public static final Image USER_IMG = new Image(Main.class
            .getResourceAsStream("/images/user.png"));

    /**
     * Image representing the Sora chatbot avatar in the GUI.
     * <p>
     * Loaded from resource path {@code /images/sora.png}.
     * <p>
     * Image source: <a href="https://uxwing.com/cloudy-color-icon/">Sora icon</a>
     * License: All icons on this site can be used for personal, commercial,
     *          and client projects without attribution.
     */
    public static final Image SORA_IMG = new Image(Main.class
            .getResourceAsStream("/images/sora.png"));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Sora sora;

    /**
     * Initialize the main window controller.
     * <p>
     * Binds the vertical scroll property of the scroll pane to the height
     * of the dialog container to ensure the latest message is always visible.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);

        Platform.runLater(() ->
            scrollPane.getScene().getStylesheets().add(
                    getClass().getResource("/css/theme.css").toExternalForm()
            )
        );
    }

    /**
     * Sets the Sora chatbot instance to be used by the controller.
     */
    public void setSora() {
        this.sora = new Sora(new GuiOutput(dialogContainer));
    }

    /**
     * Handles user input from the text field.
     * <p>
     * This method performs the following steps:
     * <ul>
     *     <li>Retrieves the text entered by the user</li>
     *     <li>Passes the text to the Sora instance for processing</li>
     *     <li>Adds a dialog box displaying the user's messages to the
     *     dialog content</li>
     *     <li>If the user types "bye" (case-insensitive),
     *     gracefully exits the application</li>
     * </ul>
     */
    @FXML
    private void handleUserInput() {
        String userText = this.userInput.getText();
        this.sora.processInput(this.userInput.getText());
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, USER_IMG)
        );
        this.userInput.clear();

        if (userText.toLowerCase().trim().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}

