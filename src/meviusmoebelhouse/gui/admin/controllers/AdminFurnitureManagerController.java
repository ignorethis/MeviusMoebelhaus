package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Furniture;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminFurnitureManagerController implements Initializable {

    ApplicationController applicationController = null;

    public AnchorPane mainAnchorPane;

    public Furniture currentFurniture = null;

    public TextField    searchforFurnitureByIdTextField, widthTextField, heightTextField, lengthTextField,
                        priceTextField, rebateTextField, nameTextField;
    public TextArea     descriptionTextArea;

    public Label    errorMessageLabelSearchFurniture, MessageLabelAddingFurniture;

    public Button changeFurnitureButton, deactivateFurnitureButton, addFurnitureButton;

    public AdminFurnitureManagerController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adjustButtonsForFurnitureOptions();
    }

    public void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminHome");
    }

    public void searchFurnitureByIdOCE(ActionEvent actionEvent) {
        Furniture f = applicationController.getFurnitureById(Integer.parseInt(searchforFurnitureByIdTextField.getText()));
        if(f == null){
            errorMessageLabelSearchFurniture.setText("ERROR! No furniture found with the given id");
        } else{
            fillTextFieldsWithFurnitureInformation(f);
        }
    }

    private void fillTextFieldsWithFurnitureInformation(Furniture f){
        widthTextField.setText(String.valueOf(f.getWidth()));
        heightTextField.setText(String.valueOf(f.getHeight()));
        lengthTextField.setText(String.valueOf(f.getLength()));
        priceTextField.setText(String.valueOf(f.getPrice()));
        rebateTextField.setText(String.valueOf(f.getRebate()));
        nameTextField.setText(f.getName());
        descriptionTextArea.setText(f.getDescription());
    }

    public void addFurnitureButtonOCE(ActionEvent actionEvent) {
        checkNewFurnitureInformation();

        Furniture furniture = new Furniture();
        furniture.setName(nameTextField.getText());
        furniture.setDescription(descriptionTextArea.getText());
        furniture.setWidth(Float.parseFloat(widthTextField.getText().replace(",", ".")));
        furniture.setHeight(Float.parseFloat(heightTextField.getText().replace(",", ".")));
        furniture.setLength(Float.parseFloat(lengthTextField.getText().replace(",", ".")));
        furniture.setPrice(new BigDecimal(priceTextField.getText().replace(",", ".")));
        furniture.setRebate(Double.parseDouble(rebateTextField.getText().replace(",", ".")));

        applicationController.addFurnitureToDatabase(furniture);
    }

    private boolean checkNewFurnitureInformation() {
        MessageLabelAddingFurniture.setText("");

        String name=nameTextField.getText(), description=descriptionTextArea.getText();
        float width, height, length;
        double rebate;
        BigDecimal price;

        if(name.equals("")){
            MessageLabelAddingFurniture.setText("Wrong input for the new name, cannot be empty");
            return false;
        }

        if(description.equals("")){
            MessageLabelAddingFurniture.setText("Wrong input for the new description, cannot be empty");
            return false;
        }

        try{
            width = Float.parseFloat(widthTextField.getText().replace(",", "."));
            if(width <= 0)
                throw new Exception();
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new width. Must be a float number");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new width. Must be greater 0");
            return false;
        }

        try{
            height = Float.parseFloat(heightTextField.getText().replace(",", "."));
            if(height <= 0)
                throw new Exception();
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new height. Must be a float number");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new height. Must be greater 0");
            return false;
        }

        try{
            length = Float.parseFloat(lengthTextField.getText().replace(",", "."));
            if(length <= 0)
                throw new Exception();
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new length. Must be a float number");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new length. Must be greater 0");
            return false;
        }

        try{
            price = new BigDecimal(priceTextField.getText().replace(",", "."));
            if((price.compareTo(new BigDecimal("0")) <= 0))
                throw new Exception();
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new price. Must be a big decimal number");
            return false;
        } catch (Exception e){
            MessageLabelAddingFurniture.setText("Wrong input for the new price. Must be greater 0");
            return false;
        }

        try{
            rebate = Double.parseDouble(rebateTextField.getText().replace(",", "."));
            if(rebate < 0 || rebate > 100)
                throw new Exception();
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new rebate. Must be a double value");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new rebate. Must be between 0 and 100");
            return false;
        }


        return true;
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
