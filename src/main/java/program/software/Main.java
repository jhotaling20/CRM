package program.software;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

public class Main extends Application {

    private static final long THIRTY_DAYS_IN_MILLIS = 30L * 24 * 60 * 60 * 1000;

    @Override
    public void start(Stage primaryStage) throws Exception {
        long currentTime = System.currentTimeMillis();
        long lastLoginTime = getLastLoginTimestamp();
        boolean rememberMe = getRememberMeStatus();


        if (rememberMe && currentTime - lastLoginTime < THIRTY_DAYS_IN_MILLIS) {
            // Load main UI if last login was within 30 days
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Static_Menu.fxml")));
            primaryStage.setScene(new Scene(root));
        } else {
            // Otherwise, show the login screen
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            primaryStage.setScene(new Scene(root));
        }
        primaryStage.show();
    }

    private long getLastLoginTimestamp() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        return prefs.getLong("lastLoginTime", 0);
    }
    private boolean getRememberMeStatus() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        return prefs.getBoolean("rememberMe", false);
    }
}

