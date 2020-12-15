package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
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


    ApplicationController applicationController;

    int customerListCounter = 0, staffListCounter = 0, adminListCounter = 0;


    public AnchorPane mainAnchorPane,
            listAnchorPane1, listAnchorPane2, listAnchorPane3,
            listAnchorPane4, listAnchorPane5, listAnchorPane6, listAnchorPane7;

    public Label listAnchorPaneNameLabel1, listAnchorPaneNameLabel2, listAnchorPaneNameLabel3,
            listAnchorPaneNameLabel4, listAnchorPaneNameLabel5, listAnchorPaneNameLabel6,
            listAnchorPaneNameLabel7,
            listAnchorPaneRoleLabel1, listAnchorPaneRoleLabel2,
            listAnchorPaneRoleLabel3, listAnchorPaneRoleLabel4, listAnchorPaneRoleLabel5,
            listAnchorPaneRoleLabel6, listAnchorPaneRoleLabel7;

    public Button anchorPaneListPreviousButton, anchorPaneListNextButton, editUser1, editUser2, editUser3, editUser4,
            editUser5, editUser6, editUser7, addUserButton;

    public ToggleGroup accountChoice;

    public RadioButton customerRadioButton, staffRadioButton, adminRadioButton;

    public TextField searchByIdTextField;

    List<Customer> allCustomers = new ArrayList<>();
    List<Staff> allStaffs = new ArrayList<>();
    List<Staff> allAdmins = new ArrayList<>();

    ArrayList<Label> listAnchorPaneNameLabels = new ArrayList<>();
    ArrayList<Label> listAnchorPaneRoleLabels = new ArrayList<>();
    ArrayList<AnchorPane> listAnchorPanes = new ArrayList<>();
    ArrayList<Button> listEditButtons = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        allCustomers = applicationController.getAllCustomers();

        List<Staff> allStaffsTemp = applicationController.getAllStaffs();

        for (Staff s : allStaffsTemp) {
            if (applicationController.getUserRoleByUserId(s.getIdUser()) == 1) {
                allAdmins.add(s);
            }
            else{
                allStaffs.add(s);
            }
        }

        for (AnchorPane a : listAnchorPanes) {
            a.setVisible(false);
        }

        anchorPaneListPreviousButton.setDisable(true);
        anchorPaneListNextButton.setDisable(true);

        for (Button a : listEditButtons) {
            a.setDisable(true);
        }
    }

    public AdminAccountManagerController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }


    public void accountTypeChosen(ActionEvent actionEvent) {

        String clickedRadioButtonId = ((RadioButton) actionEvent.getSource()).getId();

        anchorPaneListPreviousButton.setDisable(true);
        anchorPaneListNextButton.setDisable(false);

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

    public void fillListAnchorPanes(String role) {
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

    public void nextListAnchorPanes(String role) {
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

    public void previousListAnchorPanes(String role) {
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

    public void customerBorderPrev() {
        anchorPaneListPreviousButton.setDisable(customerListCounter <= 0);
    }

    public void staffBorderPrev() {
        anchorPaneListPreviousButton.setDisable(staffListCounter <= 0);
    }

    public void adminBorderPrev() {
        anchorPaneListPreviousButton.setDisable(adminListCounter <= 0);
    }

    public void customerBorderNext() {
        anchorPaneListNextButton.setDisable(customerListCounter >= allCustomers.size() - 8);
    }

    public void staffBorderNext() {
        anchorPaneListNextButton.setDisable(staffListCounter >= allStaffs.size() - 8);
    }

    public void adminBorderNext() {
        anchorPaneListNextButton.setDisable(adminListCounter >= allAdmins.size() - 8);
    }


    public void adjustAnchorPaneListPrevious(ActionEvent actionEvent) {
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

    public void adjustAnchorPaneListNext(ActionEvent actionEvent) {
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

    public void openAdminAccountEdit(ActionEvent actionEvent) throws Exception {
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

    public void back(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminHome");
    }


    public void searchByIdButtonOCE(ActionEvent actionEvent) {

        for(int i = 1; i <= listAnchorPanes.size() - 1; i++){
            listAnchorPanes.get(i).setVisible(false);
            listEditButtons.get(i).setDisable(true);
        }

        if (searchByIdTextField.getText().equals("")){
            listAnchorPaneNameLabels.get(0).setText("No input");
            listAnchorPaneRoleLabels.get(0).setText("Error");
            listAnchorPanes.get(0).setStyle("-fx-border-color: black");
            listAnchorPanes.get(0).setVisible(true);
            listEditButtons.get(0).setVisible(true);
        } else {
            int input = Integer.parseInt(searchByIdTextField.getText());
            for(int i = 0; i <= allAdmins.size() - 1; i++){
                if(allAdmins.get(i).getIdUser() == input){
                    listAnchorPaneNameLabels.get(0).setText(allAdmins.get(i).getFirstName() + "  " + allAdmins.get(i).getLastName() );
                    listAnchorPaneRoleLabels.get(0).setText("Admin");
                    listAnchorPanes.get(0).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(0).setVisible(true);
                    listEditButtons.get(0).setVisible(true);
                    editUser1.setDisable(false);
                }
                for(int j = 0; j <= allCustomers.size() - 1; j++){
                    if(allCustomers.get(j).getIdUser() == input){
                        listAnchorPaneNameLabels.get(0).setText(allCustomers.get(j).getFirstName() + "  " + allCustomers.get(j).getLastName());
                        listAnchorPaneRoleLabels.get(0).setText("Customer");
                        listAnchorPanes.get(0).setStyle("-fx-border-color: black");
                        listAnchorPanes.get(0).setVisible(true);
                        listEditButtons.get(0).setVisible(true);
                        editUser1.setDisable(false);
                    }
                }

                for(int k = 0; k <= allStaffs.size() - 1; k++){
                    if(allStaffs.get(k).getIdUser() == input){
                        listAnchorPaneNameLabels.get(0).setText(allStaffs.get(k).getFirstName() + "  " + allStaffs.get(k).getLastName());
                        listAnchorPaneRoleLabels.get(0).setText("Staff");
                        listAnchorPanes.get(0).setStyle("-fx-border-color: black");
                        listAnchorPanes.get(0).setVisible(true);
                        listEditButtons.get(0).setVisible(true);
                        editUser1.setDisable(false);
                    }
                }



            }

        }


}


    public void searchById(ActionEvent actionEvent) {
    }


    public void openAddWindow(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminAccountAdd");
    }
}
