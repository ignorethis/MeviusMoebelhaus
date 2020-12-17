package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Furniture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleItemController implements Initializable {

    @FXML private AnchorPane    mainAnchorPane;
    @FXML private Label         singleItemViewLabel;
    @FXML private ImageView     singleItemViewImage;
    @FXML private TextArea      singleItemViewDescription;
    @FXML private TextField     singleItemViewPrice, singleItemViewRebate, singleItemViewFinalPrice,
                                singleItemViewWidth, singleItemViewLength, singleItemViewHeigth;
    @FXML private Button        backToCategoryButton, backToSubcategoryButton, menuBarLogin,
                                menuBarLogout, menuBarSettings;
    @FXML private ChoiceBox     furnitureCountChoiceBox;

    private ApplicationController applicationController;

    private Furniture currentFurniture;


    public SingleItemController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get the current furniture (the furniture got clicked) and set up the single view of it
        currentFurniture = applicationController.getCurrentFurniture();

        updateUiBasedOnLoginState();

        updateSingleItemInformation();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    /**
     * Reads the amount of the choice box and passes it to the application controller which adds the furniture
     * with x amount in the shopping cart of the customer
     * @throws Exception
     */
    @FXML private void addToShoppingCart() throws Exception {
        Integer furnitureCount = (Integer) furnitureCountChoiceBox.getValue();
        applicationController.getShoppingCart().addFurniture(currentFurniture, furnitureCount);
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    @FXML private void backToHomeOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    @FXML private void backToCategoryOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Category");
    }

    @FXML private void backToSubcategoryOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Subcategory");
    }

    @FXML private void openLogin() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    @FXML private void logout() throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    @FXML private void openSettings() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    @FXML private void openShoppingCart() throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }



    //HELPING FUNCTIONS

    private void updateUiBasedOnLoginState() {
        boolean userLoggedIn = applicationController.isUserLoggedIn();
        menuBarLogin.setDisable(userLoggedIn);
        menuBarLogout.setDisable(!userLoggedIn);
        menuBarSettings.setDisable(!userLoggedIn);
    }

    private void updateSingleItemInformation() {
        backToCategoryButton.setText(applicationController.getCurrentCategory().getName());
        backToSubcategoryButton.setText(applicationController.getCurrentSubcategory().getName());
        singleItemViewLabel.setText(currentFurniture.getName());
        singleItemViewImage.setImage(currentFurniture.getImage());
        singleItemViewDescription.setText(currentFurniture.getDescription());
        singleItemViewWidth.setText(String.valueOf(currentFurniture.getWidth()));
        singleItemViewHeigth.setText(String.valueOf(currentFurniture.getHeight()));
        singleItemViewLength.setText(String.valueOf(currentFurniture.getLength()));
        singleItemViewPrice.setText(currentFurniture.getPrice() + "€");
        singleItemViewRebate.setText(currentFurniture.getRebate() + "%");
        singleItemViewFinalPrice.setText(currentFurniture.getActualPrice().toString() + "€");
        for(int i = 1; i <= 20; i++){
            furnitureCountChoiceBox.getItems().add(i);
        }
        furnitureCountChoiceBox.setValue(1);
    }
}
