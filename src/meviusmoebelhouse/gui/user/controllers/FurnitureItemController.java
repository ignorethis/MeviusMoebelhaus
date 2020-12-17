package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import meviusmoebelhouse.viewmodel.FurnitureItem;

import java.net.URL;
import java.util.ResourceBundle;

public class FurnitureItemController implements Initializable {
    @FXML private HBox furnitureItemHBox;
    @FXML private ImageView imageView;
    @FXML private Text nameText, furniturePriceText, furnitureItemPriceText;
    @FXML private Button increaseCountButton;
    @FXML private TextField amountTextField;
    @FXML private Button decreaseCountButton;

    private FurnitureItem furnitureItem;

    private ShoppingCartController shoppingCartController;

    public FurnitureItemController(FurnitureItem furnitureItem, ShoppingCartController shoppingCartController) {
        this.furnitureItem = furnitureItem;
        this.shoppingCartController = shoppingCartController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateViewFromViewModel();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void increaseCountOCE(){
        furnitureItem.increaseFurnitureCount();
        updateViewFromViewModelAndParent();
    }

    @FXML private void decreaseCountOCE() throws Exception{
        furnitureItem.decreaseFurnitureCount();
        updateViewFromViewModelAndParent();
    }

    @FXML private void changeCountOCE() {
        furnitureItem.setFurnitureCount(Integer.valueOf(amountTextField.getText()));
        updateViewFromViewModelAndParent();
    }



    //HELPING FUNCTIONS

    public void updateViewFromViewModelAndParent(){
        updateViewFromViewModel();
        shoppingCartController.updateViewFromViewModel();
    }

    public void updateViewFromViewModel(){
        imageView.setImage(furnitureItem.getFurniture().getImage());
        nameText.setText(furnitureItem.getFurniture().getName() + ": ");
        furniturePriceText.setText("   Price per unit: " + String.valueOf(furnitureItem.getFurniture().getActualPrice()) + "€ | ");
        furnitureItemPriceText.setText("Total price: " + String.valueOf(furnitureItem.getFurnitureItemPrice()) + "€ | ");
        amountTextField.setText(String.valueOf(furnitureItem.getFurnitureCount()));
    }
}
