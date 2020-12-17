package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Customer;
import meviusmoebelhouse.model.Staff;
import meviusmoebelhouse.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminAccountManagerController implements Initializable {
    @FXML private AnchorPane mainAnchorPane,
            listAnchorPane1, listAnchorPane2, listAnchorPane3,
            listAnchorPane4, listAnchorPane5, listAnchorPane6, listAnchorPane7;
    @FXML private Label listAnchorPaneNameLabel1, listAnchorPaneNameLabel2, listAnchorPaneNameLabel3,
            listAnchorPaneNameLabel4, listAnchorPaneNameLabel5, listAnchorPaneNameLabel6,
            listAnchorPaneNameLabel7,
            listAnchorPaneRoleLabel1, listAnchorPaneRoleLabel2,
            listAnchorPaneRoleLabel3, listAnchorPaneRoleLabel4, listAnchorPaneRoleLabel5,
            listAnchorPaneRoleLabel6, listAnchorPaneRoleLabel7;
    @FXML private Button anchorPaneListPreviousButton, anchorPaneListNextButton, editUser1, editUser2, editUser3, editUser4,
            editUser5, editUser6, editUser7, addUserButton, editUserById, searchByIdButton;
    @FXML private ToggleGroup accountChoice;
    @FXML private RadioButton customerRadioButton, staffRadioButton, adminRadioButton;
    @FXML private TextField searchByIdTextField;

    private ApplicationController applicationController;

    private int customerListCounter = 0, staffListCounter = 0, adminListCounter = 0, searchedID = 0;

    private List<Customer> allCustomers = new ArrayList<>();
    private List<Staff> allStaffs = new ArrayList<>();
    private List<Staff> allAdmins = new ArrayList<>();

    private ArrayList<Label> listAnchorPaneNameLabels = new ArrayList<>();
    private ArrayList<Label> listAnchorPaneRoleLabels = new ArrayList<>();
    private ArrayList<AnchorPane> listAnchorPanes = new ArrayList<>();
    private ArrayList<Button> listEditButtons = new ArrayList<>();


    public AdminAccountManagerController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        allCustomers = applicationController.getAllCustomers();

        filterAdminsFromStaffs();

        fillListAnchorPaneElements();

        adjustAnchorPaneElementsFunctionality();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void openAddWindow(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminAccountAdd");
    }

    @FXML private void openAdminAccountEditById(ActionEvent actionEvent) throws Exception {
        RadioButton selectedToggle = (RadioButton) accountChoice.getSelectedToggle();
        User temp = applicationController.getUserByUserId(searchedID);
        applicationController.openAccountManagerEdit(mainAnchorPane, temp);
    }

    @FXML private void adjustAnchorPaneListPrevious(ActionEvent actionEvent) {
        RadioButton selectedToggle = (RadioButton) accountChoice.getSelectedToggle();
        if (adminRadioButton.equals(selectedToggle)) {
            previousListAnchorPanes("Admin");
            adminBorderNext();
            adminBorderPrev();

        } else if (staffRadioButton.equals(selectedToggle)) {

            previousListAnchorPanes("Staff");
            staffBorderNext();
            staffBorderPrev();

        } else if (customerRadioButton.equals(selectedToggle)) {

            previousListAnchorPanes("Customer");
            customerBorderNext();
            customerBorderPrev();
        }
    }

    @FXML private void adjustAnchorPaneListNext(ActionEvent actionEvent) {
        RadioButton selectedToggle = (RadioButton) accountChoice.getSelectedToggle();
        if (adminRadioButton.equals(selectedToggle)) {
            nextListAnchorPanes("Admin");
            adminBorderNext();
            adminBorderPrev();

        } else if (staffRadioButton.equals(selectedToggle)) {
            nextListAnchorPanes("Staff");
            staffBorderNext();
            staffBorderPrev();

        } else if (customerRadioButton.equals(selectedToggle)) {
            nextListAnchorPanes("Customer");
            customerBorderNext();
            customerBorderPrev();

        }
    }

    @FXML private void openAdminAccountEdit(ActionEvent actionEvent) throws Exception {
        RadioButton selectedToggle = (RadioButton) accountChoice.getSelectedToggle();

        String buttonName = ((Button) actionEvent.getSource()).getId();
        int buttonId = Integer.parseInt(buttonName.substring(buttonName.length() - 1)) - 1;

        User temp = null;

        if (adminRadioButton.equals(selectedToggle)) {
            temp = applicationController.getUserByUserId(allAdmins.get(adminListCounter + buttonId).getIdUser());
        } else if (staffRadioButton.equals(selectedToggle)) {
            temp = applicationController.getUserByUserId(allStaffs.get(staffListCounter + buttonId).getIdUser());
        } else if (customerRadioButton.equals(selectedToggle)) {
            temp = applicationController.getUserByUserId(allCustomers.get(customerListCounter + buttonId).getIdUser());
        }
        applicationController.openAccountManagerEdit(mainAnchorPane, temp);
    }

    @FXML private void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminHome");
    }

    @FXML private void searchByIdButtonOCE(ActionEvent actionEvent) {

        for(int i = 1; i <= listAnchorPanes.size() - 1; i++){
            listAnchorPanes.get(i).setVisible(false);
        }
        for(int i = 0; i<= listEditButtons.size() - 1; i++){
            listEditButtons.get(i).setDisable(true);
        }
        anchorPaneListNextButton.setDisable(true);
        editUserById.setDisable(false);
        if (searchByIdTextField.getText().equals("")){
            listAnchorPaneNameLabels.get(0).setText("No input");
            listAnchorPaneRoleLabels.get(0).setText("Error");
            listAnchorPanes.get(0).setStyle("-fx-border-color: black");
            listAnchorPanes.get(0).setVisible(true);
        } else {
            int input = Integer.parseInt(searchByIdTextField.getText());
            for(int i = 0; i <= allAdmins.size() - 1; i++){
                if(allAdmins.get(i).getIdUser() == input){
                    listAnchorPaneNameLabels.get(0).setText(allAdmins.get(i).getFirstName() + "  " + allAdmins.get(i).getLastName() );
                    listAnchorPaneRoleLabels.get(0).setText("Admin");
                    listAnchorPanes.get(0).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(0).setVisible(true);
                    listEditButtons.get(0).setVisible(true);

                }
                for(int j = 0; j <= allCustomers.size() - 1; j++){
                    if(allCustomers.get(j).getIdUser() == input){
                        listAnchorPaneNameLabels.get(0).setText(allCustomers.get(j).getFirstName() + "  " + allCustomers.get(j).getLastName());
                        listAnchorPaneRoleLabels.get(0).setText("Customer");
                        listAnchorPanes.get(0).setStyle("-fx-border-color: black");
                        listAnchorPanes.get(0).setVisible(true);
                        listEditButtons.get(0).setVisible(true);

                    }
                }
                for(int k = 0; k <= allStaffs.size() - 1; k++){
                    if(allStaffs.get(k).getIdUser() == input){
                        listAnchorPaneNameLabels.get(0).setText(allStaffs.get(k).getFirstName() + "  " + allStaffs.get(k).getLastName());
                        listAnchorPaneRoleLabels.get(0).setText("Staff");
                        listAnchorPanes.get(0).setStyle("-fx-border-color: black");
                        listAnchorPanes.get(0).setVisible(true);
                        listEditButtons.get(0).setVisible(true);
                    }
                }
            }
        }
        searchedID = Integer.parseInt(searchByIdTextField.getText());
    }

    @FXML private void accountTypeChosen(ActionEvent actionEvent) {

        String clickedRadioButtonId = ((RadioButton) actionEvent.getSource()).getId();
        searchByIdButton.setDisable(false);
        anchorPaneListPreviousButton.setDisable(true);
        anchorPaneListNextButton.setDisable(false);
        editUserById.setDisable(true);
        for (Button a : listEditButtons) {
            a.setDisable(false);
        }

        adminListCounter = 0;
        customerListCounter = 0;
        staffListCounter = 0;

        for (AnchorPane a : listAnchorPanes) {
            a.setVisible(false);
        }

        switch (clickedRadioButtonId) {
            case "customerRadioButton":
                //get all customers out of database and display
                fillListAnchorPanes("Customer");
                customerBorderNext();
                customerBorderPrev();
                break;
            case "adminRadioButton":
                fillListAnchorPanes("Admin");
                adminBorderNext();
                adminBorderPrev();
                //get all admins out of database and display
                break;
            case "staffRadioButton":
                fillListAnchorPanes("Staff");
                staffBorderNext();
                staffBorderPrev();
                //get all staffs out of database and display
                break;
        }

    }




    //HELPING FUNCTIONS


    private void fillListAnchorPaneElements() {
        listAnchorPanes.addAll(Arrays.asList(listAnchorPane1, listAnchorPane2, listAnchorPane3,
                listAnchorPane4, listAnchorPane5, listAnchorPane6, listAnchorPane7));
        listAnchorPaneNameLabels.addAll(Arrays.asList(listAnchorPaneNameLabel1, listAnchorPaneNameLabel2,
                listAnchorPaneNameLabel3, listAnchorPaneNameLabel4, listAnchorPaneNameLabel5,
                listAnchorPaneNameLabel6, listAnchorPaneNameLabel7));
        listAnchorPaneRoleLabels.addAll(Arrays.asList(listAnchorPaneRoleLabel1, listAnchorPaneRoleLabel2,
                listAnchorPaneRoleLabel3, listAnchorPaneRoleLabel4, listAnchorPaneRoleLabel5,
                listAnchorPaneRoleLabel6, listAnchorPaneRoleLabel7));
        listEditButtons.addAll(Arrays.asList(editUser1, editUser2, editUser3, editUser4,
                editUser5, editUser6, editUser7));
    }

    private void fillListAnchorPanes(String role) {
        switch (role) {
            case "Customer":
                for (int i = 0; (i + customerListCounter) <= allCustomers.size() - 1 && i <= listAnchorPanes.size() - 1; i++) {
                    listAnchorPaneNameLabels.get(i).setText(
                            allCustomers.get(i + customerListCounter).getLastName()
                                    + ", " + allCustomers.get(i + customerListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Customer");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                    listEditButtons.get(i).setVisible(true);
                }
                for (int i = 6; ((i + customerListCounter) > allCustomers.size() - 1) && i >= 0; i--) {
                    listAnchorPaneNameLabels.get(i).setText("");
                    listAnchorPaneRoleLabels.get(i).setText("");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(false);
                    listEditButtons.get(i).setVisible(false);
                }
                break;
            case "Staff":
                for (int i = 0; (i + staffListCounter) <= allStaffs.size() - 1 && i <= listAnchorPanes.size() - 1; i++) {
                    listAnchorPaneNameLabels.get(i).setText(
                            allStaffs.get(i + staffListCounter).getLastName()
                                    + ", " + allStaffs.get(i + staffListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Staff");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                    listEditButtons.get(i).setVisible(true);
                }
                for (int i = 6; ((i + staffListCounter) > allStaffs.size() - 1) && i >= 0; i--) {
                    listAnchorPaneNameLabels.get(i).setText("");
                    listAnchorPaneRoleLabels.get(i).setText("");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(false);
                    listEditButtons.get(i).setVisible(false);
                }

                break;
            case "Admin":
                for (int i = 0; (i + adminListCounter) <= allAdmins.size() - 1 && i <= listAnchorPanes.size() - 1; i++) {
                    listAnchorPaneNameLabels.get(i).setText(
                            allAdmins.get(i + staffListCounter).getLastName()
                                    + ", " + allAdmins.get(i + adminListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Admin");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                    listEditButtons.get(i).setVisible(true);
                }
                for (int i = 6; ((i + adminListCounter) > allAdmins.size() - 1) && i >= 0; i--) {
                    listAnchorPaneNameLabels.get(i).setText("");
                    listAnchorPaneRoleLabels.get(i).setText("");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(false);
                    listEditButtons.get(i).setVisible(false);
                }
                break;
        }
    }

    private void nextListAnchorPanes(String role) {
        switch (role) {
            case "Customer":
                customerListCounter = customerListCounter + 7;
                break;
            case "Staff":
                staffListCounter = staffListCounter + 7;
                break;
            case "Admin":
                adminListCounter = adminListCounter + 7;
        }
        fillListAnchorPanes(role);
    }

    private void previousListAnchorPanes(String role) {
        switch (role) {
            case "Customer":
                customerListCounter = customerListCounter - 7;
                break;
            case "Staff":
                staffListCounter = staffListCounter - 7;
                break;
            case "Admin":
                adminListCounter = adminListCounter - 7;
        }
        fillListAnchorPanes(role);
    }

    private void customerBorderPrev() {
        anchorPaneListPreviousButton.setDisable(customerListCounter <= 0);
    }

    private void staffBorderPrev() {
        anchorPaneListPreviousButton.setDisable(staffListCounter <= 0);
    }

    private void adminBorderPrev() {
        anchorPaneListPreviousButton.setDisable(adminListCounter <= 0);
    }

    private void customerBorderNext() {
        anchorPaneListNextButton.setDisable(customerListCounter >= allCustomers.size() - 8);
    }

    private void staffBorderNext() {
        anchorPaneListNextButton.setDisable(staffListCounter >= allStaffs.size() - 8);
    }

    private void adminBorderNext() {
        anchorPaneListNextButton.setDisable(adminListCounter >= allAdmins.size() - 8);
    }


    private void adjustAnchorPaneElementsFunctionality() {
        for (AnchorPane a : listAnchorPanes) {
            a.setVisible(false);
        }

        for (Button a : listEditButtons) {
            a.setDisable(true);
        }

        anchorPaneListPreviousButton.setDisable(true);
        anchorPaneListNextButton.setDisable(true);
        editUserById.setDisable(true);
        searchByIdButton.setDisable(true);
    }

    private void filterAdminsFromStaffs() {
        List<Staff> allStaffsTemp = applicationController.getAllStaffs();
        for (Staff s : allStaffsTemp) {
            if (applicationController.getUserRoleByUserId(s.getIdUser()) == 1) {
                allAdmins.add(s);
            }
            else{
                allStaffs.add(s);
            }
        }
    }
}
