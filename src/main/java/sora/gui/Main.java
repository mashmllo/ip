package sora.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Main class for the Sora JavaFx GUI application.
 * <p>
 * This class extends {@link Application} and sets up the GUI components for Sora.
 * It includes a scrollable chat window, text input, and send button. User input
 * is handled and Sora responses are displayed in a dialog format.
 */
public class Main extends Application {

    private static final String MAIN_WINDOW_FXML = "/view/MainWindow.fxml";
    /**
     * Main entry point for the JavaFx application.
     * @param stage primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        assert stage != null : "Stage must not be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class
                    .getResource(MAIN_WINDOW_FXML));
            AnchorPane mainLayout = fxmlLoader.load();
            Scene scene = new Scene(mainLayout);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSora();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
