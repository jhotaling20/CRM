package program.software;

import Databases.TestDatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private TextField firstNameField, lastNameField, emailField, companyIdField, companyNameField;

    @FXML
    private PasswordField passwordField,confirmPasswordField;

    @FXML
    private Label passwordCriteriaLabel;

    @FXML
    private Button signUpButton;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    public void initialize() {
        signUpButton.setOnAction(event -> handleSignUp());
    }

    public void handleSignUp() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String companyId = companyIdField.getText();
        String companyName = companyNameField.getText();
        String password = passwordField.getText();

        // Check if companyId is empty and companyName is empty
        if ((companyId == null || companyId.isEmpty()) && (companyName == null || companyName.isEmpty())) {
            passwordCriteriaLabel.setText("Company Name is required if Company ID is not provided.");
            passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Check if companyId is provided and companyName is not empty
        if ((companyId != null && !companyId.isEmpty()) && (companyName != null && !companyName.isEmpty())) {
            passwordCriteriaLabel.setText("Only one of Company ID or Company Name should be provided.");
            passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (isValidPassword(password)) {
            password = passwordEncoder.encode(password); // Hash the password
            if (registerUser(firstName, lastName, email, companyId, companyName, password)) {
                // Successfully registered
                passwordCriteriaLabel.setText("Registration successful!");
                passwordCriteriaLabel.setStyle("-fx-text-fill: green;");
            } else {
                // Registration failed, handle error
                passwordCriteriaLabel.setText("Registration failed!");
                passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            passwordCriteriaLabel.setText("Password does not meet criteria!");
            passwordCriteriaLabel.setStyle("-fx-text-fill: red;");
        }
    }
    public static String generateHashedID(String uniqueData) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Combine current timestamp, unique data, and a random number
            Random secureRandom = new SecureRandom();
            String dataToHash = Instant.now().toString() + uniqueData + secureRandom.nextInt();

            byte[] hash = md.digest(dataToHash.getBytes());

            // Take the first 6 bytes of the hash and encode in base64 to get 8 characters
            byte[] truncatedHash = new byte[6];
            System.arraycopy(hash, 0, truncatedHash, 0, 6);
            String base64ID = Base64.getUrlEncoder().withoutPadding().encodeToString(truncatedHash);

            // Add one more character from the hash to make it 9 characters
            return base64ID + Integer.toHexString(hash[6] & 0xF);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean registerUser(String firstName, String lastName, String email,
                                 String companyId, String companyName, String hashedPassword) {
        String sql;
        String uniqueData2 = email + firstName + lastName;
        String user_ID = generateHashedID(uniqueData2);

        if (companyId == null || companyId.isEmpty()) {
            if (companyName == null || companyName.isEmpty()) {
                // Neither Company ID nor Company Name is provided (This case should not happen due to the validation in handleSignUp, but it's a safety measure)
                return false;
            }
            // Only Company Name is provided
            String uniqueData = companyName;
            companyId = generateHashedID(uniqueData);

            sql = "INSERT INTO users (user_id, first_name, last_name, email, company_name, password, company_id) VALUES (?, ?, ?, ?, ?, ? , ?)";
        } else {
            // Only Company ID is provided
            sql = "INSERT INTO users (user_id, first_name, last_name, email, password, company_id) VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (Connection connection = new TestDatabaseConnection().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            if (companyId == null || companyId.isEmpty()) {
                pstmt.setString(4, companyName);
                pstmt.setString(5, hashedPassword);
            } else {
                pstmt.setString(4, companyId);
                pstmt.setString(5, hashedPassword);
            }

            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

}
