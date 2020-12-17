package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.User;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessageLabel;
    @FXML private Button loginButton, registerButton, backToHomeButton;

    private ApplicationController applicationController;

    public LoginController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void loginOCE() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = applicationController.getUserToLogIn(username, password);
        if (user == null){
            errorMessageLabel.setText("Wrong username or password.");
        }
        else {
            applicationController.setCurrentUser(user);
            if(user.isAdmin()){
                applicationController.switchScene(mainAnchorPane, "AdminHome");
            } else {
                applicationController.switchScene(mainAnchorPane, "Home");
            }
        }
    }

    @FXML private void registerOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane,"Register");
    }

    @FXML private void backToHomeOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }



    //HELPING FUNCTIONS

}
