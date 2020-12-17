package meviusmoebelhouse.gui.user.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Category;
import meviusmoebelhouse.model.Furniture;
import meviusmoebelhouse.model.Subcategory;
import meviusmoebelhouse.model.User;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private Button   homeSalesSliderLeftButton, homeSalesSliderRightButton,
            homeCategoriesSliderLeftButton, homeCategoriesSliderRightButton,
            menuBarLogin, menuBarLogout, menuBarSettings, menuBarAdminSection;
    @FXML private AnchorPane   homeSalesSliderPane1, homeSalesSliderPane2,
            homeSalesSliderPane3, homeSalesSliderPane4,
            homeCategoriesSliderPane1, homeCategoriesSliderPane2,
            homeCategoriesSliderPane3, homeCategoriesSliderPane4;
    @FXML private ImageView    homeSalesImage1, homeSalesImage2, homeSalesImage3, homeSalesImage4,
            homeCategoriesImage1, homeCategoriesImage2, homeCategoriesImage3, homeCategoriesImage4;
    @FXML private Label welcomeMessageLabel;

    private ApplicationController applicationController;

    private int counterSales = 0;        //Index of which first furniture in sales is showing (0/1/2/3/4/5...)
    private int counterCategories = 0;   //Index of which first category in categories is showing (0/1/2/3/4/5...)

    private ArrayList<ImageView> allSalesImageViews = new ArrayList<>(); //List with all homeSalesImageViews
    private ArrayList<ImageView> allCategoryImageViews = new ArrayList<>(); //List with all homeCategoryImageViews

    private List<Furniture> allSalesFurnitures = new ArrayList<>(); //List with all sales images
    private List<Category> allCategories = new ArrayList<>(); //List with all category images


    public HomeController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeMessageLabel.setText("Welcome to our awesome Mevius Möbelhaus!");
        fillImageViewListsWithAllImageViews();

        updateUiBasedOnLoginState();
        //load all furnitures with rebates in the allSalesFurnitures list
        fillAllFurnitureSalesList();
        //load all categories in allCategories list
        allCategories = applicationController.getAllCategories();
        //show the images of the sales and categories in the image views
        showSalesImages();

        showCategoryImages();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    /**
     * Adjusts the images in the image views showing the previous image in the list
     */
    @FXML private void homeSalesSliderLeftOCE() { //slides the sales images of the slider to the left
        if(counterSales > 0){
            counterSales-- ;
            showSalesImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the next image in the list
     */
    @FXML private void homeSalesSliderRightOCE() { //slides the sales images of the slider to the right
        if(counterSales < allSalesFurnitures.size() - allSalesImageViews.size()){
            counterSales++ ;
            showSalesImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the previous image in the list
     */
    @FXML private void homeCategoriesSliderLeftOCE() { //slides the category images of the slider to the left
        if(counterCategories > 0){
            counterCategories--;
            showCategoryImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the next image in the list
     */
    @FXML private void homeCategoriesSliderRightOCE() { //slides the category images of the slider to the right
        if(counterCategories < allCategories.size() - allCategoryImageViews.size()){
            counterCategories++;
            showCategoryImages();
        }
    }

    /**
     * Passes the Image that got clicked to the Application controller which opens the single item view of
     * the furnitures image clicked
     * @param mouseEvent mouseEvent
     * @throws Exception exception
     */
    @FXML private void openSingleView(MouseEvent mouseEvent) throws Exception {
        int idOfFurniture = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
        applicationController.openSingleView(idOfFurniture, mainAnchorPane);
    }

    @FXML private void openCategory(MouseEvent mouseEvent) throws Exception {
        int idOfCategory = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
        applicationController.openCategory(idOfCategory, mainAnchorPane);
    }

    @FXML private void openLogin() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    @FXML private void openSettings() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    @FXML private void openShoppingCart() throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    @FXML private void logout() throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    @FXML private void testGui(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "AdminHome");
    }



    //HELPING FUNCTIONS

    /**
     * Fills all furniture sales image views with furniture sales images
     * Disables the unused furniture sales image views
     */
    public void showSalesImages(){
        for(int i = 0; i < allSalesImageViews.size() && i < allSalesFurnitures.size(); i++){
            allSalesImageViews.get(i).setImage(
                    allSalesFurnitures.get(counterSales + i).getImage());
            allSalesImageViews.get(i).setId(String.valueOf(
                    allSalesFurnitures.get(counterSales + i).getIdFurniture()));
        }

        //make unused imageViews invisable
        for(int i = allSalesImageViews.size() - 1; i > allSalesFurnitures.size() - 1; i--){
            allSalesImageViews.get(i).setVisible(false);
        }

        //Disable Buttons of Sales if it is negative or too big
        homeSalesSliderRightButton.setDisable(counterSales >= allSalesFurnitures.size() - 4);
        homeSalesSliderLeftButton.setDisable(counterSales <= 0);
    }

    /**
     * Fills all category image views with category images
     * Disables the unused category image views
     */
    public void showCategoryImages(){
        for(int i = 0; i < allCategoryImageViews.size() && i < allCategories.size(); i++){
            allCategoryImageViews.get(i).setImage(allCategories
                    .get(counterCategories + i).getImage());
            allCategoryImageViews.get(i).setId(String.valueOf(allCategories
                    .get(counterCategories + i).getIdCategory()));
        }

        //make unused imageViews invisable
        for(int i = allCategoryImageViews.size() - 1; i > allCategories.size() - 1; i--){
            allCategoryImageViews.get(i).setDisable(true);
        }

        //Disable Buttons of Categories if it is negative or too big
        homeCategoriesSliderRightButton.setDisable(counterCategories >= allCategories.size() - 4);
        homeCategoriesSliderLeftButton.setDisable(counterCategories <= 0);
    }

    private void updateUiBasedOnLoginState() {
        boolean userLoggedIn = applicationController.isUserLoggedIn();
        menuBarLogin.setDisable(userLoggedIn);
        menuBarLogout.setDisable(!userLoggedIn);
        menuBarSettings.setDisable(!userLoggedIn);
        User currentUser = applicationController.getCurrentUser();
        if(currentUser != null){
            if(currentUser.getIdUserRole() == 1 || currentUser.getIdUserRole() == 2){
                menuBarAdminSection.setVisible(true);
            }
        }
    }

    private void fillImageViewListsWithAllImageViews() {
        allSalesImageViews.addAll(Arrays.asList(    homeSalesImage1, homeSalesImage2,
                homeSalesImage3, homeSalesImage4));
        allCategoryImageViews.addAll(Arrays.asList( homeCategoriesImage1, homeCategoriesImage2,
                homeCategoriesImage3, homeCategoriesImage4));
    }

    private void fillAllFurnitureSalesList() {
        HashMap<Subcategory, List<Furniture>> allFurnituresBySubcategory = applicationController.getAllFurnituresBySubcategory();
        for(List<Furniture> furnitureList : allFurnituresBySubcategory.values()){
            for(Furniture furniture : furnitureList){
                if(furniture.getRebate() != 0){
                    allSalesFurnitures.add(furniture);
                }
            }
        }
    }
}