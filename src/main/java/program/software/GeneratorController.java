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
    public Button GenerateFiles;
    public Button GenerateInvoice;
    public Button GenerateEstimate;
    @FXML
    AnchorPane DynamicViewPlaceHolder;
    public void initialize() {
        // Set initial button styles
        GenerateFiles.getStyleClass().add("button-normal");
        GenerateInvoice.getStyleClass().add("button-normal");
        GenerateEstimate.getStyleClass().add("button-normal");

        // Add event handlers for button clicks
        GenerateFiles.setOnAction(event -> setActiveButton(GenerateFiles));
        GenerateInvoice.setOnAction(event -> setActiveButton(GenerateInvoice));
        GenerateEstimate.setOnAction(event -> setActiveButton(GenerateEstimate));
    }

    private void setActiveButton(Button activeButton) {
        // Reset styles for all buttons
        GenerateFiles.getStyleClass().remove("button-normal");
        GenerateInvoice.getStyleClass().remove("button-normal");
        GenerateEstimate.getStyleClass().remove("button-normal");

        // Set the clicked button to active
        activeButton.getStyleClass().add("button-active");
    }
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
