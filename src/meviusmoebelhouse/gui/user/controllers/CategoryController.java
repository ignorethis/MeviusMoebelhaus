package meviusmoebelhouse.gui.user.controllers;

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

import java.net.URL;
import java.util.*;

public class CategoryController implements Initializable {

    @FXML private Button   categorySalesSliderLeftButton, categorySalesSliderRightButton,
                    categoryFurnituresSliderLeftButton, categoryFurnituresSliderRightButton,
                    categoriesSubcategoriesSliderLeftButton, categoriesSubcategoriesSliderRightButton,
                    menuBarLogin, menuBarLogout, menuBarSettings;

    @FXML private ImageView    categoriesSalesImage1, categoriesSalesImage2,
            categoriesSalesImage3, categoriesSalesImage4,
            categoriesFurnituresImage1, categoriesFurnituresImage2,
            categoriesFurnituresImage3, categoriesFurnituresImage4,
            categoriesSubcategoriesImage1, categoriesSubcategoriesImage2,
            categoriesSubcategoriesImage3, categoriesSubcategoriesImage4;

    @FXML private Label categoryLabel;

    @FXML private AnchorPane mainAnchorPane;

    private ApplicationController applicationController = null;

    private int counterSales = 0;            //Index of which first furniture in sales is showing (0/1/2/3/4/5...)
    private int counterFurnitures = 0;       //Index of which first furniture in furnitures is showing (0/1/2/3/4/5...)
    private int counterSubcategories = 0;    //Index of which first category in categories is showing (0/1/2/3/4/5...)

    private Category currentCategory;

    private ArrayList<ImageView> allSalesImageViews = new ArrayList<>(); //List with all categorySalesImageViews
    private ArrayList<ImageView> allFurnitureImageViews = new ArrayList<>(); //List with all categoryFurnitureImageViews
    private ArrayList<ImageView> allSubcategoryImageViews = new ArrayList<>(); //List with all categorySubcategoryImageViews

    private ArrayList<Furniture> allSalesFurnitures = new ArrayList<>(); //List with all sales images in this category
    private ArrayList<Furniture> allNotSalesFurnitures = new ArrayList<>(); //List with all furniture images in this category
    private ArrayList<Subcategory> allSubcategories = new ArrayList<>(); //List with all subcategory images in this category


    public CategoryController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentCategory = applicationController.getCurrentCategory();

        categoryLabel.setText(currentCategory.getName());

        fillImageViewListsWithAllImageViews();

        updateUiButtonsBasedOnLoginState();

        fillFurnitureAndSubcategoryLists();

        showSalesImages();

        showFurnitureImages();

        showSubcategoryImages();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void backToHomeOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    @FXML private void openLogin() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    @FXML private void logout() throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    @FXML private void openSettings() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    @FXML private void openShoppingCart() throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    @FXML private void categorySalesSliderLeftOCE() {
        if(counterSales > 0){
            counterSales--;
            showSalesImages();
        }
    }

    @FXML private void categorySalesSliderRightOCE() {
        if(counterSales < allSalesFurnitures.size() - allSalesImageViews.size()){
            counterSales++;
            showSalesImages();
        }
    }

    @FXML private void categoryFurnituresSliderLeftOCE() {
        if(counterFurnitures > 0){
            counterFurnitures--;
            showFurnitureImages();
        }
    }

    @FXML private void categoryFurnituresSliderRightOCE() {
        if(counterFurnitures < allNotSalesFurnitures.size() - allFurnitureImageViews.size()){
            counterFurnitures++;
            showFurnitureImages();
        }
    }

    @FXML private void categoriesSubcategoriesSliderLeftOCE() {
        if(counterSubcategories > 0){
            counterSubcategories--;
            showSubcategoryImages();
        }
    }

    @FXML private void categoriesSubcategoriesSliderRightOCE() {
        if(counterSubcategories < allSubcategories.size() - allSubcategoryImageViews.size()){
            counterSubcategories++;
            showSubcategoryImages();
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

    @FXML private void openSubcategory(MouseEvent mouseEvent) throws Exception {
        int idOfSubcategory = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
        applicationController.openSubcategory(idOfSubcategory, mainAnchorPane);
    }




    //HELPING FUNCTIONS

    /**
     * Fills all furniture sales image views with furniture sales images
     * Disables the unused furniture sales image views
     */
    public void showSalesImages(){
        for(int i = 0; i < allSalesImageViews.size() && i < allSalesFurnitures.size(); i++){
            allSalesImageViews.get(i).setImage(allSalesFurnitures
                    .get(counterSales + i).getImage());
            allSalesImageViews.get(i).setId(String.valueOf(allSalesFurnitures
                    .get(counterSales + i).getIdFurniture()));
        }
        for(int i = allSalesImageViews.size() - 1; i > allSalesFurnitures.size() - 1; i--){
            allSalesImageViews.get(i).setVisible(false);
        }

        //Disable Buttons of Sales if it is negative or too big
        categorySalesSliderRightButton.setDisable((counterSales >= allSalesFurnitures.size() - 4));
        categorySalesSliderLeftButton.setDisable(counterSales <= 0);
    }

    /**
     * Fills all furniture image views with furniture images
     * Disables the unused furniture image views
     */
    public void showFurnitureImages(){
        for(int i = 0; i < allFurnitureImageViews.size() && i < allNotSalesFurnitures.size(); i++){
            allFurnitureImageViews.get(i).setImage(allNotSalesFurnitures
                    .get(counterFurnitures + i).getImage());
            allFurnitureImageViews.get(i).setId(String.valueOf(allNotSalesFurnitures
                    .get(counterFurnitures + i).getIdFurniture()));
        }
        for(int i = allFurnitureImageViews.size() - 1; i > allNotSalesFurnitures.size() - 1; i--){
            allFurnitureImageViews.get(i).setVisible(false);
        }

        //Dis/Enable Buttons of Sales if it is negative or too big
        categoryFurnituresSliderRightButton.setDisable(counterFurnitures >= allNotSalesFurnitures.size() - 4);
        categoryFurnituresSliderLeftButton.setDisable(counterFurnitures <= 0);
    }

    /**
     * Fills all subcategory image views with subcategory images
     * Disables the unused subcategory image views
     */
    public void showSubcategoryImages(){
        for(int i = 0; i < allSubcategoryImageViews.size() && i < allSubcategories.size(); i++){
            allSubcategoryImageViews.get(i).setImage(allSubcategories
                    .get(counterSubcategories + i).getImage());
            allSubcategoryImageViews.get(i).setId(String.valueOf(allSubcategories
                    .get(counterSubcategories + i).getIdSubcategory()));
        }
        for(int i = allSubcategoryImageViews.size() - 1; i > allSubcategories.size() - 1; i--){
            allSubcategoryImageViews.get(i).setVisible(false);
        }

        //Dis/Enable Buttons of Subcategory if it is negative or too big
        categoriesSubcategoriesSliderRightButton.setDisable(counterSubcategories >= allSubcategories.size() - 4);
        categoriesSubcategoriesSliderLeftButton.setDisable(counterSubcategories <= 0);
    }

    private void updateUiButtonsBasedOnLoginState() {
        boolean userLoggedIn = applicationController.isUserLoggedIn();
        menuBarLogin.setDisable(userLoggedIn);
        menuBarLogout.setDisable(!userLoggedIn);
        menuBarSettings.setDisable(!userLoggedIn);
    }

    private void fillFurnitureAndSubcategoryLists() {
        HashMap<Subcategory, List<Furniture>> allFurnituresBySubcategory = applicationController.getAllFurnituresBySubcategory();
        for(Map.Entry<Subcategory, List<Furniture>> entrySet : allFurnituresBySubcategory.entrySet()){
            if(currentCategory.getIdCategory() == entrySet.getKey().getIdCategory()){
                for(Furniture furniture : entrySet.getValue()){
                    if(furniture.getRebate() != 0){
                        allSalesFurnitures.add(furniture);
                    } else{
                        allNotSalesFurnitures.add(furniture);
                    }
                }
                allSubcategories.add(entrySet.getKey());
            }
        }
    }

    private void fillImageViewListsWithAllImageViews() {
        allSalesImageViews.addAll(Arrays.asList(        categoriesSalesImage1, categoriesSalesImage2,
                categoriesSalesImage3, categoriesSalesImage4));
        allFurnitureImageViews.addAll(Arrays.asList(    categoriesFurnituresImage1, categoriesFurnituresImage2,
                categoriesFurnituresImage3, categoriesFurnituresImage4));
        allSubcategoryImageViews.addAll(Arrays.asList(  categoriesSubcategoriesImage1, categoriesSubcategoriesImage2,
                categoriesSubcategoriesImage3, categoriesSubcategoriesImage4));
    }
}
