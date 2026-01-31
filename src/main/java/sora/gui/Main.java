package sora.gui;

import static sora.gui.MainWindow.USER_IMG;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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
     * Main Sora logic
     */
    private Sora sora;

    /**
     * Main entry point for the JavaFx application.
     * @param stage primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class
                    .getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSora();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
