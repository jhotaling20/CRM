package program.software;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
public class GeneratorController {
    @FXML
    AnchorPane DynamicViewPlaceHolder;
    public void GenEstimate() throws IOException {
        try{
            AnchorPane Estimate = FXMLLoader.load(getClass().getResource("Estimate.fxml"));
            DynamicViewPlaceHolder.getChildren().setAll(Estimate);
        }
        catch(IOException e) {
            e.printStackTrace();
        }


    }
}
