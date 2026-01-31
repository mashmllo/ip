package sora.gui;

import javafx.application.Application;

/**
 * Launcher class to start the GUI application for Sora.
 * <p>
 * This class exist to work around JavaFx classpath/module issues when running
 * with Gradle. It's only responsibility is to launch the {@link Main} JavaFx application.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
