package meviusmoebelhouse.gui.admin.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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


    public AnchorPane   mainAnchorPane,
                        listAnchorPane1, listAnchorPane2, listAnchorPane3,
                        listAnchorPane4,listAnchorPane5, listAnchorPane6, listAnchorPane7;

    public Label    listAnchorPaneNameLabel1,  listAnchorPaneNameLabel2,  listAnchorPaneNameLabel3,
                    listAnchorPaneNameLabel4,  listAnchorPaneNameLabel5,  listAnchorPaneNameLabel6,
                    listAnchorPaneNameLabel7,
                    listAnchorPaneRoleLabel1, listAnchorPaneRoleLabel2,
                    listAnchorPaneRoleLabel3, listAnchorPaneRoleLabel4, listAnchorPaneRoleLabel5,
                    listAnchorPaneRoleLabel6, listAnchorPaneRoleLabel7;

    public Button   anchorPaneListPrevious, anchorPaneListNext, editUser1, editUser2, editUser3, editUser4,
                    editUser5, editUser6, editUser7, addUserButton;

    public ToggleGroup accountChoice;

    public RadioButton customerRadioButton, staffRadioButton, adminRadioButton;

    ArrayList<Customer> allCustomers = new ArrayList<>();
    ArrayList<Staff> allStaffs = new ArrayList<>();
    ArrayList<Staff> allAdmins = new ArrayList<>(); //Admin class? change

    ArrayList<Label> listAnchorPaneNameLabels = new ArrayList<>();
    ArrayList<Label> listAnchorPaneRoleLabels = new ArrayList<>();
    ArrayList<AnchorPane> listAnchorPanes = new ArrayList<>();
    ArrayList<Button> listEditButtons = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listAnchorPanes.addAll(Arrays.asList(listAnchorPane1, listAnchorPane2, listAnchorPane3,
                            listAnchorPane4,listAnchorPane5, listAnchorPane6, listAnchorPane7));
        listAnchorPaneNameLabels.addAll(Arrays.asList(listAnchorPaneNameLabel1,  listAnchorPaneNameLabel2,
                            listAnchorPaneNameLabel3, listAnchorPaneNameLabel4,  listAnchorPaneNameLabel5,
                            listAnchorPaneNameLabel6, listAnchorPaneNameLabel7));
        listAnchorPaneRoleLabels.addAll(Arrays.asList(listAnchorPaneRoleLabel1, listAnchorPaneRoleLabel2,
                            listAnchorPaneRoleLabel3, listAnchorPaneRoleLabel4, listAnchorPaneRoleLabel5,
                            listAnchorPaneRoleLabel6, listAnchorPaneRoleLabel7));

        listEditButtons.addAll(Arrays.asList(editUser1, editUser2, editUser3, editUser4,
                editUser5, editUser6, editUser7));

        allCustomers = (ArrayList<Customer>) applicationController.getAllCustomers();
        allStaffs = (ArrayList<Staff>) applicationController.getAllStaffs();

        List<Staff> staffToRemove = new ArrayList<>();

        for(Staff s : allStaffs){
            if(applicationController.getUserRoleByUserId(s.getIdUser()) == 1){
                allAdmins.add(s);
                staffToRemove.add(s);
            }
        }
        for(Staff s : staffToRemove){
            allStaffs.remove(s);
        }

        for(AnchorPane a : listAnchorPanes){
            a.setVisible(false);
        }

        anchorPaneListPrevious.setDisable(true);
        anchorPaneListNext.setDisable(true);

 //       customerRadioButton.setSelected(true);
//        fillListAnchorPanes("Customer");

        for(Button a : listEditButtons){
            a.setDisable(true);
        }


    }

    public AdminAccountManagerController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }


    public void accountTypeChosen(ActionEvent actionEvent) {
        anchorPaneListPrevious.setDisable(true);
        anchorPaneListNext.setDisable(false);

        for(Button a : listEditButtons){
            a.setDisable(false);
        }

        adminListCounter = 0;
        customerListCounter = 0;
        staffListCounter = 0;

        for(AnchorPane a : listAnchorPanes){
            a.setVisible(false);
        }

        switch (((RadioButton) actionEvent.getSource()).getId()){
            case "customerRadioButton":
                //get all customers out of database and display
                fillListAnchorPanes("Customer");
                break;
            case "adminRadioButton":
                fillListAnchorPanes("Admin");
                //get all admins out of database and display
                break;
            case "staffRadioButton":
                fillListAnchorPanes("Staff");
                //get all staffs out of database and display
                break;
        }

    }

    public void fillListAnchorPanes(String role){
        switch (role){
            case "Customer":
                for(int i = 0; (i + customerListCounter) <= allCustomers.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allCustomers.get(i + customerListCounter).getLastName()
                            + ", " + allCustomers.get(i + customerListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Customer");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
            case "Staff":
                for(int i = 0; (i + staffListCounter) <= allStaffs.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allStaffs.get(i + staffListCounter).getLastName()
                                    + ", " + allStaffs.get(i + staffListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Staff");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
            case "Admin":
                for(int i = 0; (i + adminListCounter) <= allAdmins.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allAdmins.get(i + staffListCounter).getLastName()
                                    + ", " + allAdmins.get(i + adminListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Admin");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
        }
    }

    public void nextListAnchorPanes(String role){

        switch (role){
            case "Customer":

                customerListCounter = customerListCounter + 7;
                for(int i = 0; (i + customerListCounter) <= allCustomers.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allCustomers.get(i + customerListCounter).getLastName()
                                    + ", " + allCustomers.get(i + customerListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Customer");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                for(int i = 6; ((i + customerListCounter) > allCustomers.size() - 1) && i >= 0; i--){
                    listAnchorPaneNameLabels.get(i).setText("");
                    listAnchorPaneRoleLabels.get(i).setText("");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(false);
                }
                break;
            case "Staff":

                staffListCounter = staffListCounter + 7;
                for(int i = 0; (i + staffListCounter) <= allStaffs.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allStaffs.get(i + staffListCounter).getLastName()
                                    + ", " + allStaffs.get(i + staffListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Staff");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                for(int i = 6; ((i + staffListCounter) > allStaffs.size() - 1) && i >= 0; i--){
                    listAnchorPaneNameLabels.get(i).setText("");
                    listAnchorPaneRoleLabels.get(i).setText("");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(false);
                }
                break;
            case "Admin":
                adminListCounter = adminListCounter + 7;
                for(int i = 0; (i + adminListCounter) <= allAdmins.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allAdmins.get(i + staffListCounter).getLastName()
                                    + ", " + allAdmins.get(i + adminListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Admin");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                for(int i = 6; ((i + adminListCounter) > allAdmins.size() - 1) && i >= 0; i--){
                    listAnchorPaneNameLabels.get(i).setText("");
                    listAnchorPaneRoleLabels.get(i).setText("");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(false);
                }
        }
    }

    public void previousListAnchorPanes(String role){

        switch (role){
            case "Customer":
                customerListCounter = customerListCounter - 7;
                for(int i = 0; (i + customerListCounter) <= allCustomers.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allCustomers.get(i + customerListCounter).getLastName()
                                    + ", " + allCustomers.get(i + customerListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Customer");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
            case "Staff":
                staffListCounter = staffListCounter - 7;
                for(int i = 0; (i + staffListCounter) <= allStaffs.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allStaffs.get(i + staffListCounter).getLastName()
                                    + ", " + allStaffs.get(i + staffListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Staff");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
            case "Admin":
                adminListCounter = adminListCounter - 7;
                for(int i = 0; (i + adminListCounter) <= allAdmins.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allAdmins.get(i + staffListCounter).getLastName()
                                    + ", " + allAdmins.get(i + adminListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Admin");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
        }
    }

    public void customerBorderPrev(){
        anchorPaneListPrevious.setDisable(customerListCounter <= 0);
   }

   public void staffBorderPrev() {
       anchorPaneListPrevious.setDisable(staffListCounter <= 0);
   }

   public void adminBorderPrev(){
       anchorPaneListPrevious.setDisable(adminListCounter <= 0);
   }

    public void customerBorderNext(){
        anchorPaneListNext.setDisable(customerListCounter >= allCustomers.size() - 8);
    }

    public void staffBorderNext() {
        anchorPaneListNext.setDisable(staffListCounter >= allStaffs.size() - 8);
    }

    public void adminBorderNext(){
        anchorPaneListNext.setDisable(adminListCounter >= allAdmins.size() - 8);
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
            temp = applicationController.getUserRepository().getByIdUser(allAdmins.get(buttonId + adminListCounter).getIdUser());
        } else if (staffRadioButton.equals(selectedToggle)) {
            temp = applicationController.getUserRepository().getByIdUser(allStaffs.get(buttonId + staffListCounter).getIdUser());
        } else if (customerRadioButton.equals(selectedToggle)) {
            temp = applicationController.getUserRepository().getByIdUser(allCustomers.get(buttonId + customerListCounter).getIdUser());
        }
        applicationController.openAccountManagerEdit(mainAnchorPane, temp);
    }

    public void back(ActionEvent actionEvent) {
    }
}
