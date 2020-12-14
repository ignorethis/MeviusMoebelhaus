package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Furniture;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminFurnitureManagerController implements Initializable {

    ApplicationController applicationController = null;

    public AnchorPane mainAnchorPane;

    public Furniture currentFurniture = null;

    public TextField    searchforFurnitureByIdTextField, widthTextField, heightTextField, lengthTextField,
                        priceTextField, rebateTextField, nameTextField, descriptionTextField;

    public Button changeFurnitureButton, deactivateFurnitureButton, addFurnitureButton;

    public AdminFurnitureManagerController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adjustButtonsForFurnitureOptions();
    }

    public void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminHomeController");
    }

    public void searchFurnitureByIdOCE(ActionEvent actionEvent) {
        Furniture f = applicationController.getFurnitureById(Integer.parseInt(searchforFurnitureByIdTextField.getText()));

        if(f == null){

        }
    }

    public void addFurnitureButtonOCE(ActionEvent actionEvent) {
    }

    public void changeFurnitureButtonOCE(ActionEvent actionEvent) {
    }

    public void deactivateFurnitureButtonOCE(ActionEvent actionEvent) {
    }

    private void adjustButtonsForFurnitureOptions(){
        Boolean currentFurnitureIsNull = (currentFurniture == null);
            addFurnitureButton.setDisable(!currentFurnitureIsNull);
            changeFurnitureButton.setDisable(currentFurnitureIsNull);
            deactivateFurnitureButton.setDisable(currentFurnitureIsNull);
    }
}
