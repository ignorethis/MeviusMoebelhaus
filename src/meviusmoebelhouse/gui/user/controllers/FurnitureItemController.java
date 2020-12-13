package meviusmoebelhouse.gui.user.controllers;

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
    private FurnitureItem furnitureItem;
    private ShoppingCartController shoppingCartController;

    public HBox furnitureItemHBox;
    public ImageView imageView;
    public Text nameText, furniturePriceText, furnitureItemPriceText;
    public Button increaseCountButton;
    public TextField amountTextField;
    public Button decreaseCountButton;

    public FurnitureItemController(FurnitureItem furnitureItem, ShoppingCartController shoppingCartController) {
        this.furnitureItem = furnitureItem;
        this.shoppingCartController = shoppingCartController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateViewFromViewModel();
    }

    public void increaseCountOCE(){
        furnitureItem.increaseFurnitureCount();
        updateViewFromViewModelAndParent();
    }

    public void decreaseCountOCE() throws Exception{
        furnitureItem.decreaseFurnitureCount();
        updateViewFromViewModelAndParent();
    }

    public void changeCountOCE() {
        furnitureItem.setFurnitureCount(Integer.valueOf(amountTextField.getText()));
        updateViewFromViewModelAndParent();
    }

    public void updateViewFromViewModelAndParent(){
        updateViewFromViewModel();
        shoppingCartController.updateViewFromViewModel();
    }

    public void updateViewFromViewModel(){
        //TODO imageView.setImage(furnitureItem.getFurniture().getPictureAddress());
        nameText.setText(furnitureItem.getFurniture().getName());
        furniturePriceText.setText(String.valueOf(furnitureItem.getFurniture().getActualPrice()));
        furnitureItemPriceText.setText(String.valueOf(furnitureItem.getFurnitureItemPrice()));
        amountTextField.setText(String.valueOf(furnitureItem.getFurnitureCount()));
    }
}
