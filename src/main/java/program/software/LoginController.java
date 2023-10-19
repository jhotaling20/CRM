package program.software;

import Databases.TestDatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.prefs.Preferences;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Hyperlink signUpLink;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    public void initialize() {
        signInButton.setOnAction(event -> handleSignIn());
        signUpLink.setOnAction(event -> handleSignUp());
    }

    public void handleSignIn() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(email, password)) {
            feedbackLabel.setText("Login successful!");
            feedbackLabel.setStyle("-fx-text-fill: green;");

            // Transition to the main UI
            loadMainUI();

            // Store the current timestamp as the last successful login
            storeLoginTimestamp();

        } else {
            feedbackLabel.setText("Invalid email or password!");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
    }
    private void storeLoginTimestamp() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.putLong("lastLoginTime", System.currentTimeMillis());
    }


    private void loadMainUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Static_Menu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window
            ((Stage) signInButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean authenticateUser(String email, String enteredPassword) {
        String sql = "SELECT password FROM users WHERE email = ?";

        try (Connection connection = new TestDatabaseConnection().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                return passwordEncoder.matches(enteredPassword, storedHashedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void handleSignUp() {
        try {
            // Load the Signup.fxml layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Parent signupRoot = loader.load();

            // Get the current scene's window
            Stage stage = (Stage) signInButton.getScene().getWindow();

            // Set the new scene
            Scene signupScene = new Scene(signupRoot);
            stage.setScene(signupScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately, e.g., show an error dialog
        }
    }


}
