package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Customer;
import meviusmoebelhouse.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField usernameTextField, firstnameTextField, lastnameTextField,
            IBANTextField, emailAddressTextField, passwordTextField;
    @FXML private DatePicker birthdayDatePicker;
    @FXML private Label errorMessageLabel,passwordLabel;
    @FXML private Button saveButton,cancelButton,changePasswordButton;

    private ApplicationController applicationController;
    private User user;
    private Customer customer;


    public SettingsController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            user = applicationController.getCurrentUser();
            customer = applicationController.getCustomerByUserId(user.getIdUser());
            usernameTextField.setText(user.getUsername());
            firstnameTextField.setText(customer.getFirstName());
            lastnameTextField.setText(customer.getLastName());
            IBANTextField.setText(customer.getIBAN());
            emailAddressTextField.setText(customer.getEmailAddress());
            birthdayDatePicker.setValue(customer.getBirthday());
        }
        catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void changePasswordOCE(){
        if (passwordLabel.isDisable()){
            passwordLabel.setDisable(false);
            passwordTextField.setDisable(false);
        }
    }

    @FXML private void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    @FXML private void saveOCE() throws Exception{
        customer.setFirstName(firstnameTextField.getText());
        customer.setLastName(lastnameTextField.getText());
        customer.setBirthday(birthdayDatePicker.getValue());
        customer.setIBAN(IBANTextField.getText());
        customer.setEmailAddress(emailAddressTextField.getText());
        applicationController.changeCustomerInDatabase(customer);

        if (!passwordLabel.isDisable()) {
            user.setPassword(passwordTextField.getText());
            applicationController.changeUserInDatabase(user);
        }

        applicationController.switchScene(mainAnchorPane, "Home");
    }



    //HELPING FUNCTIONS
}
