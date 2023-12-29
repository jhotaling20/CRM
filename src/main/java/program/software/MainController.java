package program.software;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController {

    public VBox profileOptions;
    public Hyperlink logOut;
    public Button loadDashboardButton;
    public Button loadGeneratorButton;
    @FXML
    private AnchorPane dynamicContentPlaceholder;
    @FXML
    private Button profileButton;

    public void fxmlloader(String url){
        try{
            AnchorPane dynamic = FXMLLoader.load(getClass().getResource(url));
            AnchorPane.setTopAnchor(dynamic, 0.0);
            AnchorPane.setBottomAnchor(dynamic, 0.0);
            AnchorPane.setLeftAnchor(dynamic, 0.0);
            AnchorPane.setRightAnchor(dynamic, 0.0);
            dynamicContentPlaceholder.getChildren().setAll(dynamic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDashboard() {

        fxmlloader("Dashboard.fxml");
    }
    public void loadGenerator(){
        fxmlloader("Generator.fxml");
    }

    public void handleProfileButtonClick(ActionEvent actionEvent) {
        boolean isVisible = profileOptions.isVisible();
        profileOptions.setVisible(!isVisible);
        profileOptions.setManaged(!isVisible);
    }

    public void handleLogout(ActionEvent event) {

        // Load login screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            Preferences prefs = Preferences.userNodeForPackage(Main.class);
            prefs.putBoolean("rememberMe", false);
        } catch (IOException e) {
            e.printStackTrace(); // handle exception
        }
    }
    private void initialize() {
        // Initialize any necessary logic here
    }

    public void handleButtonAction(ActionEvent event) {
        Button btn = (Button) event.getSource();
        toggleButtonStyle(btn);
        // Load appropriate content based on the button
        if (btn.equals(loadDashboardButton)) {
            loadDashboard();
        } else if (btn.equals(loadGeneratorButton)) {
            loadGenerator();
        }
        // Add conditions for other buttons if necessary
    }

    private void toggleButtonStyle(Button button) {
        if (!button.getStyleClass().contains("button-pressed")) {
            button.getStyleClass().add("button-pressed");
        } else {
            button.getStyleClass().remove("button-pressed");
        }
    }

}
