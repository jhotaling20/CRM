package program.software;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class GeneratorController {
    @FXML public Button GenerateFiles;
    @FXML public Button GenerateInvoice;
    @FXML public Button GenerateEstimate;
    @FXML AnchorPane DynamicViewPlaceHolder;
    @FXML public Button PresetsTab;

    // Constructor
    public GeneratorController() {
        // Constructor code here, if necessary
    }

    @FXML
    public void GenEstimate() throws IOException {
        loadFXML("Estimate.fxml");
    }

    @FXML
    public void loadPresetTab() throws IOException {
        loadFXML("CompanyInitialization.fxml");
    }

    private void loadFXML(String fxmlFileName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlFileName));
            DynamicViewPlaceHolder.getChildren().setAll(pane);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Other methods...


    public void GenInvoice() {
        loadFXML("Invoice.fxml");
    }

    public void GenFiles() {
    }
}
