package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Customer;
import meviusmoebelhouse.model.Staff;
import meviusmoebelhouse.model.User;

import java.net.URL;
import java.util.*;

public class AdminAccountEditController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField usernameTextField, firstnameTextField, lastnameTextField, IBANTextField, emailAddressTextField, passwordTextField, addressTextField;
    @FXML private DatePicker birthdayDatePicker;
    @FXML private Label errorMessageLabel,passwordLabel;
    @FXML private Button saveButton,cancelButton,changePasswordButton;
    @FXML private GridPane gridPane;

    private ApplicationController applicationController;

    private User userToCreate = null;
    private Staff staffDataOfUser = null;
    private Customer customerDataOfUser = null;


    public AdminAccountEditController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInformationFields();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS


    @FXML private void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    @FXML private void changePasswordOCE(ActionEvent actionEvent) {
        passwordTextField.setDisable(false);
    }

    @FXML private void saveOCE() throws Exception{

        if(userToCreate.getIdUserRole() == 1 || userToCreate.getIdUserRole() == 2){
            staffDataOfUser.setFirstName(firstnameTextField.getText());
            staffDataOfUser.setLastName(lastnameTextField.getText());
            applicationController.updateStaff(staffDataOfUser);

            if (!passwordTextField.isDisable()) {
                userToCreate.setPassword(passwordTextField.getText());
                applicationController.changeUserInDatabase(userToCreate);
            }

            applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
            applicationController.changeStaffInDatabase(staffDataOfUser);

        }

        else if(userToCreate.getIdUserRole() == 3){
            customerDataOfUser.setFirstName(firstnameTextField.getText());
            customerDataOfUser.setLastName(lastnameTextField.getText());
            applicationController.changeCustomerInDatabase(customerDataOfUser);

            if (!passwordTextField.isDisable()) {
                userToCreate.setPassword(passwordTextField.getText());
                applicationController.changeUserInDatabase(userToCreate);
            }

            customerDataOfUser.setEmailAddress(emailAddressTextField.getText());
            customerDataOfUser.setDefaultShippingAddress(addressTextField.getText());
            customerDataOfUser.setIBAN(IBANTextField.getText());
            customerDataOfUser.setBirthday(birthdayDatePicker.getValue());
            applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
            applicationController.changeCustomerInDatabase(customerDataOfUser);
        }
    }




    //HELPING FUNCTIONS

    public void updateTable(){
        usernameTextField.setText(userToCreate.getUsername());
        firstnameTextField.setText(staffDataOfUser.getFirstName());
        lastnameTextField.setText(staffDataOfUser.getLastName());
        passwordLabel.setDisable(true);
    }

    private void fillInformationFields() {
        try {
            userToCreate = applicationController.getCurrentUserToChange();
            if(userToCreate.getIdUserRole() == 1 || userToCreate.getIdUserRole() == 2){
                staffDataOfUser = applicationController.getStaffByUserId(userToCreate.getIdUser());

                firstnameTextField.setText(staffDataOfUser.getFirstName());
                lastnameTextField.setText(staffDataOfUser.getLastName());
                birthdayDatePicker.setDisable(true);
                IBANTextField.setDisable(true);
                emailAddressTextField.setDisable(true);
                addressTextField.setDisable(true);

            } else{
                customerDataOfUser = applicationController.getCustomerByUserId(userToCreate.getIdUser());
                firstnameTextField.setText(customerDataOfUser.getFirstName());
                lastnameTextField.setText(customerDataOfUser.getLastName());
                IBANTextField.setText(customerDataOfUser.getIBAN());
                emailAddressTextField.setText(customerDataOfUser.getEmailAddress());
                birthdayDatePicker.setValue(customerDataOfUser.getBirthday());
            }
            usernameTextField.setText(userToCreate.getUsername());
        }
        catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }
}