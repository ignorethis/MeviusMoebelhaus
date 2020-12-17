package meviusmoebelhouse.gui.user.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.gui.user.fxmlfiles.FXML;
import meviusmoebelhouse.model.Customer;
import meviusmoebelhouse.model.Invoice;
import meviusmoebelhouse.model.InvoiceDetails;
import meviusmoebelhouse.repositories.InvoiceDetailsRepository;
import meviusmoebelhouse.repositories.InvoiceRepository;
import meviusmoebelhouse.viewmodel.FurnitureItem;
import meviusmoebelhouse.viewmodel.ShoppingCart;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable {
    private ApplicationController applicationController;

    public AnchorPane mainAnchorPane;
    public Label    furnitureName1, furnitureName2, furnitureName3,
                    furnitureDescription1, furnitureDescription2, furnitureDescription3;
    public ChoiceBox<Integer> furnitureAmount1, furnitureAmount2, furnitureAmount3;
    public TextField    furniturePricePerUnit1, furniturePricePerUnit2, furniturePricePerUnit3;
    public Text totalPriceText;
    public VBox shoppingCartItemsContainer;
    public Button menuBarLogin, menuBarLogout, menuBarSettings;

    public ShoppingCartController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateUiBasedOnLoginState();

        updateViewFromViewModel();
    }

    public void openLogin(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void logout() throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    public void openSettings(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    public void backToHomeOCE(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void orderOCE() throws Exception{
        if (!applicationController.isUserLoggedIn()){
            applicationController.switchScene(mainAnchorPane,"Login");
            return;
        }

        ShoppingCart shoppingCart = applicationController.getShoppingCart();
        Customer customer = applicationController.getCustomerByUserId(applicationController.getCurrentUser().getIdUser());

        Invoice invoice = new Invoice();
        invoice.setIdCustomer(customer.getIdCustomer());
        invoice.setFirstName(customer.getFirstName());
        invoice.setLastName(customer.getLastName());
        invoice.setShippingAddress(customer.getDefaultShippingAddress());
        invoice.setTotalPrice(shoppingCart.getTotalPrice());
        applicationController.addInvoiceToDatabase(invoice);

        for (FurnitureItem furnitureItem: shoppingCart.getShoppingList()) {
            InvoiceDetails invoiceDetails = new InvoiceDetails();
            invoiceDetails.setIdInvoice(invoice.getIdInvoice());
            invoiceDetails.setIdFurniture(furnitureItem.getFurniture().getIdFurniture());
            invoiceDetails.setAmount(furnitureItem.getFurnitureCount());
            invoiceDetails.setPrice(furnitureItem.getFurniture().getActualPrice());
            invoiceDetails.setTotalPrice(furnitureItem.getFurnitureItemPrice());
            applicationController.addInvoiceDetailsToDatabase(invoiceDetails);

        }

        shoppingCart.clear();
        applicationController.switchScene(mainAnchorPane, "Home");
        //TODO Print PDF of Invoice
    }

    public void updateViewFromViewModel(){
        shoppingCartItemsContainer.getChildren().clear();
        ShoppingCart shoppingCart = applicationController.getShoppingCart();
        for (FurnitureItem furnitureItem: shoppingCart.getShoppingList()) {
            try {
                //furnitureItem.fxml
                FXMLLoader loader = new FXMLLoader(FXML.class.getResource("FurnitureItem.fxml"));
                loader.setControllerFactory(fic -> new FurnitureItemController(furnitureItem, this));
                shoppingCartItemsContainer.getChildren().add(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        totalPriceText.setText(String.valueOf(shoppingCart.getTotalPrice()));
    }

    private void updateUiBasedOnLoginState() {
        boolean userLoggedIn = applicationController.isUserLoggedIn();
        menuBarLogin.setDisable(userLoggedIn);
        menuBarLogout.setDisable(!userLoggedIn);
        menuBarSettings.setDisable(!userLoggedIn);
    }
}
