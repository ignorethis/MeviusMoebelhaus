package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
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

    private ApplicationController applicationController;

    private User userToCreate = null;
    private Staff staffDataOfUser = null;
    private Customer customerDataOfUser = null;

    public AnchorPane mainAnchorPane;
    public TextField usernameTextField, firstnameTextField, lastnameTextField, IBANTextField, emailAddressTextField, passwordTextField, addressTextField;
    public DatePicker birthdayDatePicker;
    public Label errorMessageLabel,passwordLabel, errorLabel;
    public Button saveButton,cancelButton,changePasswordButton;
    public GridPane gridPane;
    public RadioButton addAdminAccountRadio, addStuffAccountRadio, addCustomerAccountRadio;

    boolean adminSelected = false;
    boolean staffSelected = false;
    boolean customerSelected = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AdminAccountAddController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }


    public void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    public void saveOCE() throws Exception{

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
                //newCustomerToAdd.setDefaultShippingAddress(addressTextField.getText()); - unable to store shipping address - will be fixed
                // Customer user won't be added to Customer Table - will be fixed

                applicationController.addNewCustomerToDatabase(newCustomerToAdd);
                errorLabel.setText("Added new user successfully");
            }

        }
        catch (SQLiteException e){
            errorLabel.setText("Incorrect");
        }

    }
    

    public void selectAdminAccountType(ActionEvent actionEvent) {
        birthdayDatePicker.setDisable(true);
        IBANTextField.setDisable(true);
        emailAddressTextField.setDisable(true);
        adminSelected = true;
        staffSelected = false;
        customerSelected = false;

    }

    public void selectStuffAccountType(ActionEvent actionEvent) {
        birthdayDatePicker.setDisable(true);
        IBANTextField.setDisable(true);
        emailAddressTextField.setDisable(true);
        adminSelected = false;
        staffSelected = true;
        customerSelected = false;
    }

    public void selectCustomerAccountType(ActionEvent actionEvent) {
        birthdayDatePicker.setDisable(false);
        IBANTextField.setDisable(false);
        emailAddressTextField.setDisable(false);
        adminSelected = false;
        staffSelected = false;
        customerSelected = true;
    }
}