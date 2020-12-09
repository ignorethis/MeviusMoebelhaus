package meviusmoebelhouse.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable {

    private final ApplicationController applicationController = Main.getApplicationController();

    public AnchorPane mainAnchorPane;
    public Label    furnitureName1, furnitureName2, furnitureName3,
                    furnitureDescription1, furnitureDescription2, furnitureDescription3;
    public ChoiceBox<Integer> furnitureAmount1, furnitureAmount2, furnitureAmount3;
    public TextField    furniturePricePerUnit1, furniturePricePerUnit2, furniturePricePerUnit3,
                        totalPriceField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 20; i++){
            furnitureAmount1.getItems().add(i);
            furnitureAmount2.getItems().add(i);
            furnitureAmount3.getItems().add(i);
        }
    }

    public void openLogin(ActionEvent actionEvent) throws IOException {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void logout(ActionEvent actionEvent) {
    }

    public void openSettings(ActionEvent actionEvent) throws IOException {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    public void backToHomeOCE(ActionEvent actionEvent) throws IOException {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void showPreviousPageOCE(ActionEvent actionEvent) {
    }

    public void showNextPageOCE(ActionEvent actionEvent) {
    }
}
