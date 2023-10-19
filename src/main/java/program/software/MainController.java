package program.software;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
public class MainController {
    @FXML
    private AnchorPane dynamicContentPlaceholder;
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
}
