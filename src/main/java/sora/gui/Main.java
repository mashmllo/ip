package sora.gui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sora.Sora;
import sora.ui.GuiOutput;


/**
 * Main class for the Sora JavaFx GUI application.
 * <p>
 * This class extends {@link Application} and sets up the GUI components for Sora.
 * It includes a scrollable chat window, text input, and send button. User input
 * is handled and Sora responses are displayed in a dialog format.
 */
public class Main extends Application {

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

    /**
     * Scrollable container for chat messages
     */
    private ScrollPane scrollPane;

    /**
     * Vertical container to hold user and Sora dialog box
     */
    private VBox dialogContainer;

    /**
     * Text field where user types messages
     */
    private TextField userInput;

    /**
     * Button for sending user message
     */
    private Button sendButton;

    /**
     * Scene representing the main GUI layout
     */
    private Scene scene;

    /**
     * Main Sora logic
     */
    private Sora sora = new Sora();

    /**
     * Main entry point for the JavaFx application.
     * @param stage primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        this.scrollPane = new ScrollPane();
        this.dialogContainer = new VBox();
        this.scrollPane.setContent(dialogContainer);
        this.sora = new Sora(new GuiOutput(dialogContainer));

        this.userInput = new TextField();
        this.sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(this.scrollPane, this.userInput, this.sendButton);

        this.scene = new Scene(mainLayout);

        stage.setScene(this.scene);
        stage.show();

        stage.setTitle("Sora");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        this.scrollPane.setPrefSize(385, 535);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.scrollPane.setVvalue(1.0);
        this.scrollPane.setFitToWidth(true);

        this.dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        this.dialogContainer.heightProperty().addListener((observable) ->
                this.scrollPane.setVvalue(1.0));
        this.userInput.setPrefWidth(325.0);
        this.sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(this.scrollPane, 1.0);

        AnchorPane.setBottomAnchor(this.sendButton, 1.0);
        AnchorPane.setRightAnchor(this.sendButton, 1.0);

        AnchorPane.setLeftAnchor(this.userInput, 1.0);
        AnchorPane.setBottomAnchor(this.userInput, 1.0);

        this.sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        this.userInput.setOnAction((event) -> {
            handleUserInput();
        });
        dialogContainer.heightProperty().addListener((observable)
                -> scrollPane.setVvalue(1.0));
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
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
