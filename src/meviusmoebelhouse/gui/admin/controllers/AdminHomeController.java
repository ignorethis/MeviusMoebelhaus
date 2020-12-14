package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminHomeController implements Initializable {

    private ApplicationController applicationController = null;

    public AnchorPane mainAnchorPane;

    public AdminHomeController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logout(ActionEvent actionEvent) throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    public void openFunctionFurniture(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminFurnitureManager");
    }

    public void openFunctionAccountManagement(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    public void openFunctionInvoices(ActionEvent actionEvent) {
    }

    public void openFunctionInventory(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }
}