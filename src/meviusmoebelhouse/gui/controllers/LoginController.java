package meviusmoebelhouse.gui.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;

import java.io.IOException;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final ApplicationController applicationController = Main.getApplicationController();

    public AnchorPane mainAnchorPane;
    public TextField usernameField, passwordField;
    public Label errorMessageLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginOCE() {
    }

    public void registerOCE() {
    }

    public void backToHomeOCE() throws IOException {
        applicationController.switchScene(mainAnchorPane, "Home");
    }
}
