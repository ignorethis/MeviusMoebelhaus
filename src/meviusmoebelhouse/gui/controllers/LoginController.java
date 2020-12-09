package meviusmoebelhouse.gui.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.User;
import meviusmoebelhouse.repositories.UserRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private ApplicationController applicationController = null;

    public AnchorPane mainAnchorPane;
    public TextField usernameField, passwordField;
    public Label errorMessageLabel;

    public LoginController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginOCE() {
   /*     userRepository.getByUsernameAndPassword(usernameField.getText(),passwordField.getText());
        if (){

        }*/
    }

    public void registerOCE() {
    }

    public void backToHomeOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }
}
