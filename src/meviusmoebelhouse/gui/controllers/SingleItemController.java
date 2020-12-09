package meviusmoebelhouse.gui.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Furniture;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleItemController implements Initializable {

    private final ApplicationController applicationController = Main.getApplicationController();

    public Furniture currentFurniture;
    public AnchorPane mainAnchorPane;
    public Label singleItemViewLabel;
    public ImageView singleItemViewImage;
    public TextArea singleItemViewDescription;
    public TextField singleItemViewPrice, singleItemViewRebate, singleItemViewFinalPrice;
    public Button backToCategoryButton, backToSubcategoryButton;
    public ChoiceBox furnitureAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get the current furniture (the furniture got clicked) and set up the single view of it
        currentFurniture = applicationController.getCurrentFurniture();

        backToCategoryButton.setText(applicationController.getCurrentCategory().getName());
        backToSubcategoryButton.setText(applicationController.getCurrentSubcategory().getName());
        singleItemViewLabel.setText(currentFurniture.getName());
        singleItemViewImage.setImage(applicationController.getFurnitureImageByFurniture(currentFurniture));
        singleItemViewDescription.setText(currentFurniture.getDescription());
        singleItemViewPrice.setText(currentFurniture.getPrice() + "€");
        singleItemViewRebate.setText(currentFurniture.getRebate() + "%");
        singleItemViewFinalPrice.setText(currentFurniture.getActualPrice().toString() + "€");
        for(int i = 1; i <= 20; i++){
            furnitureAmount.getItems().add(i);
        }
        furnitureAmount.setValue(1);
    }

    /**
     * Reads the amount of the choice box and passes it to the application controller which adds the furniture
     * with x amount in the shopping cart of the customer
     * @throws IOException
     */
    public void addToShoppingCart() throws IOException {
        applicationController.addFurnitureToShoppingCart((Integer) furnitureAmount.getValue());
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    public void backToHomeOCE() throws IOException {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void backToCategoryOCE() throws IOException {
        applicationController.switchScene(mainAnchorPane, "Category");
    }

    public void backToSubcategoryOCE() throws IOException {
        applicationController.switchScene(mainAnchorPane, "Subcategory");
    }

    public void openLogin() throws IOException {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void logout() {

    }

    public void openSettings() throws IOException {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    public void openShoppingCart() throws IOException {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }
}
