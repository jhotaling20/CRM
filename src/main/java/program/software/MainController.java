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

import java.io.IOException;
public class MainController {

    public VBox profileOptions;
    public Hyperlink logOut;
    @FXML
    private AnchorPane dynamicContentPlaceholder;
    @FXML
    private Button profileButton;
    public void fxmlloader(String url){
        try{
            AnchorPane dynamic = FXMLLoader.load(getClass().getResource(url));
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
        } catch (IOException e) {
            e.printStackTrace(); // handle exception
        }
    }
}
