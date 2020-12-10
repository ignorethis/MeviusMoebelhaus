package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.User;
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

    public void loginOCE() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = applicationController.getUserRepository().getByUsernameAndPassword(username, password);
        if (user == null){
            errorMessageLabel.setText("Wrong username or password");
        }
        else {
            applicationController.setCurrentUser(user);
            if(applicationController.getUserRoleRepository().getByIdUserRole(user.getIdUser()).getIdUserRole() == 1){
                applicationController.switchScene(mainAnchorPane, "AdminHome");
            } else {
                applicationController.switchScene(mainAnchorPane, "Home");
            }
        }
    }

    public void registerOCE() {
    }

    public void backToHomeOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }
}
