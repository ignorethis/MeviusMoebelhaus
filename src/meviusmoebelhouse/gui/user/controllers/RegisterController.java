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
import meviusmoebelhouse.model.UserRoleEnum;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable{
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField usernameTextField, firstnameTextField, lastnameTextField,
                            IBANTextField, emailAddressTextField, defaultShippingAddressTextField,
                            passwordTextField;
    @FXML private DatePicker birthdayDatePicker;
    @FXML private Label errorMessageLabel,passwordLabel;
    @FXML private Button saveButton,cancelButton,changePasswordButton;

    private ApplicationController applicationController;

    public RegisterController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    @FXML private void saveOCE() throws Exception{
        User registeredUser = new User();
        registeredUser.setUserRole(UserRoleEnum.Customer);
        registeredUser.setUsername(usernameTextField.getText());
        registeredUser.setPassword(passwordTextField.getText());
        registeredUser.setFailedLoginAttempts(0);
        applicationController.addNewUserToDatabase(registeredUser);
        applicationController.setCurrentUser(registeredUser);

        Customer customer = new Customer();
        customer.setFirstName(firstnameTextField.getText());
        customer.setLastName(lastnameTextField.getText());
        customer.setBirthday(birthdayDatePicker.getValue());
        customer.setIBAN(IBANTextField.getText());
        customer.setEmailAddress(emailAddressTextField.getText());
        customer.setDefaultShippingAddress(defaultShippingAddressTextField.getText());
        customer.setIdUser(registeredUser.getIdUser());
        applicationController.addNewCustomerToDatabase(customer);

        applicationController.switchScene(mainAnchorPane, "Home");
    }



    //HELPING FUNCTIONS
}
