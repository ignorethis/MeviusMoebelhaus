package meviusmoebelhouse.gui.admin.controllers;

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

    private ApplicationController applicationController;

    private User userToCreate = null;
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
            userToCreate = applicationController.getCurrentUserToChange();
            if(userToCreate.getIdUserRole() == 1 || userToCreate.getIdUserRole() == 2){
                staffDataOfUser = applicationController.getStaffByUserId(userToCreate.getIdUser());

                firstnameTextField.setText(staffDataOfUser.getFirstName());
                lastnameTextField.setText(staffDataOfUser.getLastName());
                birthdayDatePicker.setDisable(true);
                IBANTextField.setDisable(true);
                emailAddressTextField.setDisable(true);

            } else{
                customerDataOfUser = applicationController.getCustomerByUserId(userToCreate.getIdUser());

                firstnameTextField.setText(customerDataOfUser.getFirstName());
                lastnameTextField.setText(customerDataOfUser.getLastName());
                //birthdayDatePicker.setChronology(197785456);
                IBANTextField.setText(customerDataOfUser.getIBAN());
                emailAddressTextField.setText(customerDataOfUser.getEmailAddress());
            }
            usernameTextField.setText(userToCreate.getUsername());
        }
        catch (Exception e) {
            errorMessageLabel.setText(e.getMessage());
        }
    }

    public AdminAccountEditController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }


    public void cancelOCE() throws Exception{
        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");
    }

    public void saveOCE() throws Exception{
        staffDataOfUser.setFirstName(firstnameTextField.getText());
        staffDataOfUser.setLastName(lastnameTextField.getText());
        applicationController.getStaffRepository().update(staffDataOfUser);

        if (!passwordLabel.isDisable()) {
            userToCreate.setPassword(passwordTextField.getText());
            applicationController.getUserRepository().update(userToCreate);
        }

        applicationController.switchScene(mainAnchorPane, "AdminAccountManager");

        userToCreate = applicationController.getCurrentUser();
        staffDataOfUser = applicationController.getStaffRepository().getByIdStaff(userToCreate.getIdUser());
    }

    public void updateTable(){
        usernameTextField.setText(userToCreate.getUsername());
        firstnameTextField.setText(staffDataOfUser.getFirstName());
        lastnameTextField.setText(staffDataOfUser.getLastName());
        passwordLabel.setDisable(true);
    }
}