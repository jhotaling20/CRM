package program.software;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaterialsController {

    public VBox equationsVBox;
    @FXML
    private VBox materialsVBox;
    @FXML
    private VBox jobTypesVBox;

    @FXML
    private void handleAddMaterial(ActionEvent event) {
        HBox newMaterialHBox = new HBox(10);
        TextField newMaterialNameField = new TextField();
        newMaterialNameField.setPromptText("Material Name");
        TextField newMaterialCostField = new TextField();
        newMaterialCostField.setPromptText("Cost per unit");
        TextField newMaterialTimeField = new TextField();
        newMaterialTimeField.setPromptText("Time per unit");
        Button removeButton = new Button("-");
        removeButton.setOnAction(e -> materialsVBox.getChildren().remove(newMaterialHBox));

        newMaterialHBox.getChildren().addAll(newMaterialNameField, newMaterialCostField, newMaterialTimeField, removeButton);
        materialsVBox.getChildren().add(newMaterialHBox);
    }

    @FXML
    private void handleAddJobType(ActionEvent event) {
        HBox newJobTypeHBox = new HBox(10);
        TextField newJobTypeField = new TextField();
        newJobTypeField.setPromptText("Job Type");
        Button removeButton = new Button("-");
        removeButton.setOnAction(e -> jobTypesVBox.getChildren().remove(newJobTypeHBox));
        newJobTypeHBox.getChildren().addAll(newJobTypeField, removeButton);
        jobTypesVBox.getChildren().add(newJobTypeHBox);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Handle the submission of all data here
        equationsVBox.setVisible(true);
        populateVariables(); // Populate ComboBoxes with saved data
    }

    private void populateVariables() {
        
    }

    public void handleAddToEquation(ActionEvent actionEvent) {
    }

    public void handleSubmitVariables(ActionEvent actionEvent) {
    }
}
