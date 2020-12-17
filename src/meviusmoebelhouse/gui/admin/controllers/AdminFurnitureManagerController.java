package meviusmoebelhouse.gui.admin.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Category;
import meviusmoebelhouse.model.Furniture;
import meviusmoebelhouse.model.Subcategory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminFurnitureManagerController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private Furniture currentFurniture = null;
    @FXML private TextField searchforFurnitureByIdTextField, widthTextField, heightTextField, lengthTextField,
            priceTextField, rebateTextField, nameTextField;
    @FXML private TextArea     descriptionTextArea;
    @FXML private ChoiceBox categoryChoiceBox, subcategoryChoiceBox, activeChoiceBox;
    @FXML private Label    errorMessageLabelSearchFurniture, MessageLabelAddingFurniture;
    @FXML private Button changeFurnitureButton, deactivateFurnitureButton, addFurnitureButton;


    private ApplicationController applicationController = null;

    public AdminFurnitureManagerController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adjustButtonsForFurnitureOptions();

        adjustChoiceBoxes();

        //create an selectedIndexPropertyListener for the category choicebox to adjust the subcategory choicebox
        setCategoryChoiceBoxOnChangeListener();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminHome");
    }

    @FXML private void searchFurnitureByIdOCE(ActionEvent actionEvent) {
        try{
            currentFurniture = applicationController.getFurnitureById(Integer.parseInt(searchforFurnitureByIdTextField.getText()));
            if(currentFurniture == null){
                throw new Exception();
            }
        } catch (Exception e){
            errorMessageLabelSearchFurniture.setText("ERROR! No furniture found with the given id");
            return;
        }
        fillFieldsWithFurnitureInformation();
        adjustButtonsForFurnitureOptions();
        adjustActiveChoiceForFurniture();
    }

    @FXML private void addFurnitureButtonOCE(ActionEvent actionEvent) throws Exception {
        Furniture furniture = new Furniture();
        if(checkAndSetNewFurnitureInformation(furniture)){
            applicationController.addFurnitureToDatabase(furniture);
            MessageLabelAddingFurniture.setText("Furniture successfully added into the database.");
        }
    }

    @FXML private void changeFurnitureButtonOCE(ActionEvent actionEvent) throws Exception {
        if(checkAndSetNewFurnitureInformation(currentFurniture)){
            applicationController.changeFurnitureInDatabase(currentFurniture);
            MessageLabelAddingFurniture.setText("Furniture successfully changed in the database.");
        }
    }

    @FXML private void deactivateFurnitureButtonOCE(ActionEvent actionEvent) throws Exception {
        applicationController.deactivateFurniture(currentFurniture);
        MessageLabelAddingFurniture.setText("Furniture successfully changed to deactive.");
    }




    //HELPING FUNCTIONS

    private void adjustChoiceBoxes() {
        for(String str : applicationController.getAllCategoryNames()){
            categoryChoiceBox.getItems().add(str);
        }
        activeChoiceBox.getItems().add("Yes");
        activeChoiceBox.getItems().add("No");
    }

    private void adjustActiveChoiceForFurniture(){
        if(currentFurniture != null){
            if(currentFurniture.getIsActive()){
                activeChoiceBox.setValue("Yes");
            } else{
                activeChoiceBox.setValue("No");
            }
        }
    }

    private boolean checkAndSetNewFurnitureInformation(Furniture furniture) {
        MessageLabelAddingFurniture.setText("");

        String name = nameTextField.getText();
        if(name.equals("")){
            MessageLabelAddingFurniture.setText("Wrong input for the new name, cannot be empty");
            return false;
        }
        furniture.setName(name);

        String description = descriptionTextArea.getText();
        if(description.equals("")){
            MessageLabelAddingFurniture.setText("Wrong input for the new description, cannot be empty");
            return false;
        }
        furniture.setDescription(description);

        try{
            float width = Float.parseFloat(widthTextField.getText().replace(",", "."));
            if(width <= 0)
                throw new Exception();
            furniture.setWidth(width);
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new width. Must be a float number");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new width. Must be greater 0");
            return false;
        }

        try{
            float height = Float.parseFloat(heightTextField.getText().replace(",", "."));
            if(height <= 0)
                throw new Exception();
            furniture.setHeight(height);
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new height. Must be a float number");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new height. Must be greater 0");
            return false;
        }

        try{
            float length = Float.parseFloat(lengthTextField.getText().replace(",", "."));
            if(length <= 0)
                throw new Exception();
            furniture.setLength(length);
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new length. Must be a float number");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new length. Must be greater 0");
            return false;
        }

        try{
            BigDecimal price = new BigDecimal(priceTextField.getText().replace(",", "."));
            if((price.compareTo(new BigDecimal("0")) <= 0))
                throw new Exception();
            furniture.setPrice(price);
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new price. Must be a big decimal number");
            return false;
        } catch (Exception e){
            MessageLabelAddingFurniture.setText("Wrong input for the new price. Must be greater 0");
            return false;
        }

        try{
            double rebate;
            if(rebateTextField.getText() != null){
                rebate = Double.parseDouble(rebateTextField.getText().replace(",", "."));
                if(rebate < 0 || rebate > 100)
                    throw new Exception();
                furniture.setRebate(rebate);
            } else {
                furniture.setRebate(0);
            }
        } catch (NumberFormatException e){
            MessageLabelAddingFurniture.setText("Wrong input for the new rebate. Must be a double value");
            return false;
        } catch (Exception e) {
            MessageLabelAddingFurniture.setText("Wrong input for the new rebate. Must be between 0 and 100");
            return false;
        }

        furniture.setIsActive(true);

        try{
            Subcategory subcategory = applicationController.getSubcategoryByName(
                    subcategoryChoiceBox.getValue().toString());
            if(subcategory == null){
                throw new Exception();
            } else{
                furniture.setIdSubcategory(subcategory.getIdSubcategory());
            }
        } catch (Exception e){
            MessageLabelAddingFurniture.setText("Wrong subcategory chosen. ERROR");
            return false;
        }

        return true;
    }

    private void setCategoryChoiceBoxOnChangeListener() {
        categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if((Integer)newValue != -1){
                    String categoryChosen = categoryChoiceBox.getItems().get((Integer) newValue).toString();
                    subcategoryChoiceBox.getItems().removeAll(subcategoryChoiceBox.getItems());

                    for(String subcategoryName : applicationController.getAllSubcategoryNamesOfCategory(categoryChosen)){
                        subcategoryChoiceBox.getItems().add(subcategoryName);
                    }
                    if(currentFurniture != null){
                        subcategoryChoiceBox.setValue(applicationController.getSubcategoryById(
                                currentFurniture.getIdSubcategory()).getName());
                    }
                }
            }
        });
    }

    private void fillFieldsWithFurnitureInformation(){
        //set textfields
        widthTextField.setText(String.valueOf(currentFurniture.getWidth()));
        heightTextField.setText(String.valueOf(currentFurniture.getHeight()));
        lengthTextField.setText(String.valueOf(currentFurniture.getLength()));
        priceTextField.setText(String.valueOf(currentFurniture.getPrice()));
        rebateTextField.setText(String.valueOf(currentFurniture.getRebate()));
        nameTextField.setText(currentFurniture.getName());
        descriptionTextArea.setText(currentFurniture.getDescription());

        //set new values for subcategories in the category of the furniture
        Subcategory subcategoryOfFurniture = applicationController.getSubcategoryById(currentFurniture.getIdSubcategory());
        Category categoryOfFurniture = applicationController.getCategoryById(subcategoryOfFurniture.getIdCategory());

        subcategoryChoiceBox.getItems().removeAll(subcategoryChoiceBox.getItems());

        for(String subcategoryName : applicationController.getAllSubcategoryNamesOfCategory(categoryOfFurniture.getName())){
            subcategoryChoiceBox.getItems().add(subcategoryName);
        }
        subcategoryChoiceBox.setValue(subcategoryChoiceBox.getItems().get(0));

        //reset categorychoicebox with category chosen the furniture is in
        categoryChoiceBox.setValue(null);
        categoryChoiceBox.getItems().removeAll(categoryChoiceBox.getItems());
        for(String str : applicationController.getAllCategoryNames()){
            categoryChoiceBox.getItems().add(str);
        }
        categoryChoiceBox.setValue(categoryOfFurniture.getName());
    }

    private void adjustButtonsForFurnitureOptions(){
        Boolean currentFurnitureIsNull = (currentFurniture == null);
            addFurnitureButton.setDisable(!currentFurnitureIsNull);
            changeFurnitureButton.setDisable(currentFurnitureIsNull);
            deactivateFurnitureButton.setDisable(currentFurnitureIsNull);
    }
}
