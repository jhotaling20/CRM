package program.software;

import Databases.TestDatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
public class SignupController {
    @FXML
    public CheckBox hasCompanyIdCheckBox;
    @FXML
    private TextField firstNameField, lastNameField, emailField, companyIdField, companyNameField;

    @FXML
    private PasswordField passwordField,confirmPasswordField;

    @FXML
    private Label passwordCriteriaLabel;

    @FXML
    private Button signUpButton;
    @FXML
    private Hyperlink backButton;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @FXML
    public void initialize() {
        signUpButton.setOnAction(event -> {
            try {
                handleSignUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnAction(event -> {
            try {
                handleBack();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        companyIdField.setDisable(true);

    }

    public void handleSignUp() throws Exception {
        boolean success = false;
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String companyId = companyIdField.getText();
        String companyName = companyNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            passwordCriteriaLabel.setText("Passwords do not match!");
            passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
            return; // Exit the method if passwords don't match
        }

        if (isValidPassword(password)) {
            password = passwordEncoder.encode(password); // Hash the password

            // Logic to handle registration based on company ID or new company
            if (hasCompanyIdCheckBox.isSelected() && !companyId.isEmpty()) {
                // Existing company logic here...
                // For now, simply registering the user with the given company ID.
                if (registerUser(firstName, lastName, email, companyId, null, password)) {
                    passwordCriteriaLabel.setText("Registration successful!");
                    success = true;
                    passwordCriteriaLabel.setStyle("-fx-text-fill: green;");
                } else {
                    passwordCriteriaLabel.setText("Registration failed!");
                    passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
                }
            } else if (!companyName.isEmpty()) {
                // New company logic here...
                // For now, creating a new company and registering the user.
                if (registerUser(firstName, lastName, email, null, companyName, password)) {
                    passwordCriteriaLabel.setText("Registration successful!");
                    success = true;
                    passwordCriteriaLabel.setStyle("-fx-text-fill: green;");
                } else {
                    passwordCriteriaLabel.setText("Registration failed!");
                    passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                passwordCriteriaLabel.setText("Please provide either a Company ID or a Company Name.");
                passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            passwordCriteriaLabel.setText("Password does not meet criteria!");
            passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
        }
        if (success) {
            handleBack();
        }
    }
    public static String generateHashedID(String uniqueData) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Combine current timestamp, unique data, and a random number
            Random secureRandom = new SecureRandom();
            String dataToHash = Instant.now().toString() + uniqueData + secureRandom.nextInt();

            byte[] hash = md.digest(dataToHash.getBytes());

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            // Return the first 9 characters of the hexadecimal string
            return hexString.substring(0, 9);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean insertCompany(String companyId, String companyName) throws Exception {
        String sql = "INSERT INTO companies (company_id, company_name) VALUES (?, ?)";
        try (Connection connection = new TestDatabaseConnection().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, companyId);
            pstmt.setString(2, companyName);
            System.out.println("Attempting to insert company_id: " + companyId);
            if (companyId.length() != 9) {
                System.err.println("Invalid company_id length: " + companyId.length());
            }
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertUser(String user_ID, String firstName, String lastName, String email, String hashedPassword, String companyId) throws Exception {
        String sql = "INSERT INTO users (user_id, first_name, last_name, email, password, company_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = new TestDatabaseConnection().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user_ID);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, hashedPassword);
            pstmt.setString(6, companyId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean registerUser(String firstName, String lastName, String email,
                                 String companyId, String companyName, String hashedPassword) throws Exception {

        String uniqueData2 = email + firstName + lastName;
        String user_ID = generateHashedID(uniqueData2);

        if (companyId == null || companyId.isEmpty()) {
            if (companyName == null || companyName.isEmpty()) {
                return false;
            }

            // Only Company Name is provided
            String uniqueData = companyName;
            companyId = generateHashedID(uniqueData);

            // Insert new company
            if (!insertCompany(companyId, companyName)) {
                return false;
            }
        }

        // Insert user
        return insertUser(user_ID, firstName, lastName, email, hashedPassword, companyId);
    }



    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    public void toggleCompanyId() {
        if (hasCompanyIdCheckBox.isSelected()) {
            companyIdField.setDisable(false);
            companyNameField.setDisable(true);
        } else {
            companyIdField.setDisable(true);
            companyNameField.setDisable(false);
        }
    }
    public void handleBack() throws Exception{
        // Transition to the main UI
        try {
            // Load new FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Setup the new stage
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            // Close the current stage
            Stage currentStage = (Stage) backButton.getScene().getWindow(); // currentButton is any control in the current scene
            currentStage.close();

            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
