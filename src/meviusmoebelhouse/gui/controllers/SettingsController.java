package meviusmoebelhouse.gui.controllers;

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
    ApplicationController applicationController = null;
    public AnchorPane mainAnchorPane;
    public TextField usernameTextField, firstnameTextField, lastnameTextField, IBANTextField, emailAddressTextField, passwordTextField;
    public DatePicker birthdayDatePicker;
    public Label errorMessageLabel,passwordLabel;
    public Button saveButton,cancelButton,changePasswordButton;
    private User user;
    private Customer customer;

    public SettingsController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.user = applicationController.getCurrentUser();
            this.customer = applicationController.getCustomerRepository().getByIdUser(user.getIdUser());
            usernameTextField.setText(user.getUsername());
            firstnameTextField.setText(customer.getFirstName());
            lastnameTextField.setText(customer.getLastName());
            IBANTextField.setText(customer.getIBAN());
            emailAddressTextField.setText(customer.getEmailAddress());
            birthdayDatePicker.setValue(customer.getBirthday().toLocalDate());
        }
        catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }
    public void changePasswordOCE(){
        if (passwordLabel.isDisable() == true){
            passwordLabel.disableProperty().setValue(false);
            passwordTextField.disableProperty().setValue(false);
        }
    }

    public void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void saveOCE() throws Exception{
        if (passwordLabel.isDisable() == true){
            customer.setFirstName(firstnameTextField.getText());
            customer.setLastName(lastnameTextField.getText());
            customer.setBirthday(birthdayDatePicker.getChronology().toString());
            customer.setIBAN(IBANTextField.getText());
            customer.setEmailAddress(emailAddressTextField.getText());

            applicationController.setCurrentUser(user);
            applicationController.getCustomerRepository().update(customer);
        }
        else{
            customer.setFirstName(firstnameTextField.getText());
            customer.setLastName(lastnameTextField.getText());
            customer.setBirthday(birthdayDatePicker.getChronology().toString());
            customer.setIBAN(IBANTextField.getText());
            customer.setEmailAddress(emailAddressTextField.getText());
            user.setPassword(passwordTextField.getText());

            applicationController.setCurrentUser(user);
            applicationController.getCustomerRepository().update(customer);
        }
        applicationController.switchScene(mainAnchorPane, "Home");
    }
}
