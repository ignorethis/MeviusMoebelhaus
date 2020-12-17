package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Customer;
import meviusmoebelhouse.model.Staff;
import meviusmoebelhouse.model.User;
import org.sqlite.SQLiteException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminAccountAddController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField usernameTextField, firstnameTextField, lastnameTextField,
                            IBANTextField, emailAddressTextField, passwordTextField, addressTextField;
    @FXML private DatePicker birthdayDatePicker;
    @FXML private Label errorMessageLabel,passwordLabel, errorLabel;
    @FXML private Button saveButton,cancelButton,changePasswordButton;
    @FXML private GridPane gridPane;
    @FXML private RadioButton addAdminAccountRadio, addStuffAccountRadio, addCustomerAccountRadio;

    private ApplicationController applicationController;

    private User userToCreate = null;
    private Staff staffDataOfUser = null;
    private Customer customerDataOfUser = null;

    private boolean adminSelected = false;
    private boolean staffSelected = false;
    private boolean customerSelected = false;


    public AdminAccountAddController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS


    @FXML private void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    @FXML private void saveOCE() throws Exception{

        User newUserToAdd = new User();
        Staff newStaffToAdd = new Staff();
        Customer newCustomerToAdd = new Customer();
        try {

            if(adminSelected || staffSelected){
                newUserToAdd.setUsername(usernameTextField.getText());
                newUserToAdd.setPassword(passwordTextField.getText());
                if(adminSelected){
                    newUserToAdd.setIdUserRole(1);
                } else newUserToAdd.setIdUserRole(2);
                newUserToAdd.setFailedLoginAttempts(0);
                applicationController.addNewUserToDatabase(newUserToAdd);
                newStaffToAdd.setIdUser(applicationController.getUserByUsername(usernameTextField.getText()));
                newStaffToAdd.setFirstName(firstnameTextField.getText());
                newStaffToAdd.setLastName(lastnameTextField.getText());
                applicationController.addNewStaffToDatabase(newStaffToAdd);
                errorLabel.setText("Added new user successfully");
            }

            if(customerSelected){
                newUserToAdd.setUsername(usernameTextField.getText());
                newUserToAdd.setPassword(passwordTextField.getText());
                newUserToAdd.setIdUserRole(3);
                applicationController.addNewUserToDatabase(newUserToAdd);

                newCustomerToAdd.setIdUser(applicationController.getUserByUsername(usernameTextField.getText()));
                newCustomerToAdd.setFirstName(firstnameTextField.getText());
                newCustomerToAdd.setLastName(lastnameTextField.getText());
                newCustomerToAdd.setBirthday(birthdayDatePicker.getValue());
                newCustomerToAdd.setIBAN(IBANTextField.getText());
                newCustomerToAdd.setEmailAddress(emailAddressTextField.getText());
                newCustomerToAdd.setDefaultShippingAddress(addressTextField.getText()); //- unable to store shipping address - will be fixed

                applicationController.addNewCustomerToDatabase(newCustomerToAdd);
                errorLabel.setText("Added new user successfully");
            }

        }
        catch (SQLiteException e){
            errorLabel.setText("Incorrect");
        }

    }

    @FXML private void selectAdminAccountType(ActionEvent actionEvent) {
        birthdayDatePicker.setDisable(true);
        IBANTextField.setDisable(true);
        emailAddressTextField.setDisable(true);
        adminSelected = true;
        staffSelected = false;
        customerSelected = false;

    }

    @FXML private void selectStuffAccountType(ActionEvent actionEvent) {
        birthdayDatePicker.setDisable(true);
        IBANTextField.setDisable(true);
        emailAddressTextField.setDisable(true);
        adminSelected = false;
        staffSelected = true;
        customerSelected = false;
    }

    @FXML private void selectCustomerAccountType(ActionEvent actionEvent) {
        birthdayDatePicker.setDisable(false);
        IBANTextField.setDisable(false);
        emailAddressTextField.setDisable(false);
        adminSelected = false;
        staffSelected = false;
        customerSelected = true;
    }




    //HELPING FUNCTIONS
}