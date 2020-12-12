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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

    public Button   anchorPaneListPrevious, anchorPaneListNext;

    public ToggleGroup accountChoice;

    public RadioButton customerRadioButton, staffRadioButton, adminRadioButton;

    ArrayList<Customer> allCustomers = new ArrayList<>();
    ArrayList<Staff> allStaffs = new ArrayList<>();
    ArrayList<Staff> allAdmins = new ArrayList<>(); //Admin class? change

    ArrayList<Label> listAnchorPaneNameLabels = new ArrayList<>();
    ArrayList<Label> listAnchorPaneRoleLabels = new ArrayList<>();
    ArrayList<AnchorPane> listAnchorPanes = new ArrayList<>();

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

        allCustomers = (ArrayList<Customer>) applicationController.getAllCustomers();
        allStaffs = (ArrayList<Staff>) applicationController.getAllStaffs();

        allAdmins.addAll(Arrays.asList(allStaffs.get(50), allStaffs.get(51), allStaffs.get(52),
                allStaffs.get(53), allStaffs.get(54), allStaffs.get(55), allStaffs.get(56), allStaffs.get(57),
                allStaffs.get(58), allStaffs.get(59), allStaffs.get(60))); //Admin class? change

        for(AnchorPane a : listAnchorPanes){
            //a.setStyle("-fx-border-color: black");;
            a.setVisible(false);
        }

        anchorPaneListPrevious.setDisable(true);
        anchorPaneListNext.setDisable(true);
    }

    public AdminAccountManagerController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }


    public void accountTypeChosen(ActionEvent actionEvent) {
        anchorPaneListPrevious.setDisable(false);
        anchorPaneListNext.setDisable(false);

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
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");;
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
            case "Staff":
                for(int i = 0; (i + staffListCounter) <= allStaffs.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allStaffs.get(i + staffListCounter).getLastName()
                                    + ", " + allStaffs.get(i + staffListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Staff");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");;
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
            case "Admin":
                for(int i = 0; (i + adminListCounter) <= allAdmins.size() - 1 && i <= listAnchorPanes.size() - 1; i++){
                    listAnchorPaneNameLabels.get(i).setText(
                            allAdmins.get(i + staffListCounter).getLastName()
                                    + ", " + allAdmins.get(i + adminListCounter).getFirstName());
                    listAnchorPaneRoleLabels.get(i).setText("Admin");
                    listAnchorPanes.get(i).setStyle("-fx-border-color: black");;
                    listAnchorPanes.get(i).setVisible(true);
                }
                break;
        }
    }

    public void adjustAnchorPaneListViewRadioButtonOCE(ActionEvent actionEvent) {
        RadioButton selectedToggle = (RadioButton) accountChoice.getSelectedToggle();
        if (adminRadioButton.equals(selectedToggle)) {
            if(((Button) actionEvent.getSource()).getId() == "anchorPaneListPrevious"){

            } else {

            }
        } else if (staffRadioButton.equals(selectedToggle)) {

        } else if (customerRadioButton.equals(selectedToggle)) {

        }
    }

    public void adjustAnchorPaneListView(String role){

    }
}
