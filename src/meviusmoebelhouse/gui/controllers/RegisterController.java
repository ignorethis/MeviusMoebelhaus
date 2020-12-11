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

public class RegisterController implements Initializable{
    ApplicationController applicationController;
    public AnchorPane mainAnchorPane;
    public TextField usernameTextField, firstnameTextField, lastnameTextField, IBANTextField, emailAddressTextField, passwordTextField;
    public DatePicker birthdayDatePicker;
    public Label errorMessageLabel,passwordLabel;
    public Button saveButton,cancelButton,changePasswordButton;

    public RegisterController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void saveOCE() throws Exception{
        User registeredUser = new User();
        registeredUser.setUsername(usernameTextField.getText());
        registeredUser.setPassword(passwordTextField.getText());
        registeredUser.setFailedLoginAttempts(0);
        applicationController.getUserRepository().create(registeredUser);
        applicationController.setCurrentUser(registeredUser);

        Customer customer = new Customer();
        customer.setFirstName(firstnameTextField.getText());
        customer.setLastName(lastnameTextField.getText());
        customer.setBirthday(birthdayDatePicker.getValue());
        customer.setIBAN(IBANTextField.getText());
        customer.setEmailAddress(emailAddressTextField.getText());
        customer.setIdUser(registeredUser.getIdUser());
        applicationController.getCustomerRepository().create(customer);

        applicationController.switchScene(mainAnchorPane, "Home");
    }
}
