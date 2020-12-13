package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AdminAccountEditController implements Initializable {

    ApplicationController applicationController;

    public AnchorPane mainAnchorPane;

    public AdminAccountEditController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void AdminAccountChangeName(ActionEvent actionEvent) {
    }

    public void AdminAccountChangeMail(ActionEvent actionEvent) {
    }

    public void AdminAccountChangePassword(ActionEvent actionEvent) {
    }

    public void AdminAccountRemoveUser(ActionEvent actionEvent) {
    }

    public void AdminAccountBackToOverview(ActionEvent actionEvent) {
    }
}