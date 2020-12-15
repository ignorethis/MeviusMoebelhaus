package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Customer;
import meviusmoebelhouse.model.Staff;
import meviusmoebelhouse.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminAccountEditController implements Initializable {

    private ApplicationController applicationController;

    private User userToChange = null;
    private Staff staffDataOfUser = null;
    private Customer customerDataOfUser = null;

    public AnchorPane mainAnchorPane;
    public TextField usernameTextField, firstnameTextField, lastnameTextField, IBANTextField, emailAddressTextField, passwordTextField;
    public DatePicker birthdayDatePicker;
    public Label errorMessageLabel,passwordLabel;
    public Button saveButton,cancelButton,changePasswordButton;
    public GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userToChange = applicationController.getCurrentUserToChange();
            if(userToChange.getIdUserRole() == 1 || userToChange.getIdUserRole() == 2){
                staffDataOfUser = applicationController.getStaffByUserId(userToChange.getIdUser());

                firstnameTextField.setText(staffDataOfUser.getFirstName());
                lastnameTextField.setText(staffDataOfUser.getLastName());

            } else{
                customerDataOfUser = applicationController.getCustomerByUserId(userToChange.getIdUser());

                firstnameTextField.setText(customerDataOfUser.getFirstName());
                lastnameTextField.setText(customerDataOfUser.getLastName());
                //birthdayDatePicker.setChronology(197785456);
                IBANTextField.setText(customerDataOfUser.getIBAN());
                emailAddressTextField.setText(customerDataOfUser.getEmailAddress());
            }
            usernameTextField.setText(userToChange.getUsername());
        }
        catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    public AdminAccountEditController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }

    public void changePasswordOCE(){
        if (passwordLabel.isDisable()){
            passwordLabel.setDisable(false);
            passwordTextField.setDisable(false);
        }
    }

    public void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    public void saveOCE() throws Exception{
        staffDataOfUser.setFirstName(firstnameTextField.getText());
        staffDataOfUser.setLastName(lastnameTextField.getText());
        applicationController.getStaffRepository().update(staffDataOfUser);

        if (!passwordLabel.isDisable()) {
            userToChange.setPassword(passwordTextField.getText());
            applicationController.getUserRepository().update(userToChange);
        }

        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");

        userToChange = applicationController.getCurrentUser();
        staffDataOfUser = applicationController.getStaffRepository().getByIdStaff(userToChange.getIdUser());
    }

    public void updateTable(){
        usernameTextField.setText(userToChange.getUsername());
        firstnameTextField.setText(staffDataOfUser.getFirstName());
        lastnameTextField.setText(staffDataOfUser.getLastName());
        passwordLabel.setDisable(true);
    }
}