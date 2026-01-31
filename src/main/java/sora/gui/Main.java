package sora.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Main class for the Sora JavaFx GUI application.
 * <p>
 * This class extends {@link Application} and sets up the GUI components for Sora.
 * It includes a scrollable chat window, text input, and send button. User input
 * is handled and Sora responses are displayed in a dialog format.
 */
public class Main extends Application {

    /**
     * Main entry point for the JavaFx application.
     * @param stage primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }
}

