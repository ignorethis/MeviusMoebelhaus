package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminHomeController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;

    private ApplicationController applicationController = null;


    public AdminHomeController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void logout(ActionEvent actionEvent) throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    @FXML private void openFunctionFurniture(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminFurnitureManager");
    }

    @FXML private void openFunctionAccountManagement(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    @FXML private void openFunctionInvoices(ActionEvent actionEvent) {
    }

    @FXML private void openFunctionInventory(ActionEvent actionEvent) {
    }

    @FXML private void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }




    //HELPING FUNCTIONS
}