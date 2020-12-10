package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    private ApplicationController applicationController = null;

    public int counterSales = 0;        //Index of which first furniture in sales is showing (0/1/2/3/4/5...)
    public int counterCategories = 0;   //Index of which first category in categories is showing (0/1/2/3/4/5...)

    public AnchorPane mainAnchorPane;

    public Button   homeSalesSliderLeftButton, homeSalesSliderRightButton,
            homeCategoriesSliderLeftButton, homeCategoriesSliderRightButton, menuBarLogin, menuBarLogout, menuBarSettings;

    public AnchorPane   homeSalesSliderPane1, homeSalesSliderPane2,
            homeSalesSliderPane3, homeSalesSliderPane4,
            homeCategoriesSliderPane1, homeCategoriesSliderPane2,
            homeCategoriesSliderPane3, homeCategoriesSliderPane4;

    public ImageView    homeSalesImage1, homeSalesImage2, homeSalesImage3, homeSalesImage4,
            homeCategoriesImage1, homeCategoriesImage2, homeCategoriesImage3, homeCategoriesImage4;

    ArrayList<ImageView> allSalesImageViews = new ArrayList<>(); //List with all homeSalesImageViews
    ArrayList<ImageView> allCategoryImageViews = new ArrayList<>(); //List with all homeCategoryImageViews

    ArrayList<Image> allSalesImages = new ArrayList<>(); //List with all sales images
    ArrayList<Image> allCategoryImages = new ArrayList<>(); //List with all category images

    public HomeController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //create bundle with all sales and all categories image views
        allSalesImageViews.addAll(Arrays.asList(    homeSalesImage1, homeSalesImage2,
                                                    homeSalesImage3, homeSalesImage4));
        allCategoryImageViews.addAll(Arrays.asList( homeCategoriesImage1, homeCategoriesImage2,
                                                    homeCategoriesImage3, homeCategoriesImage4));

        boolean userLoggedIn = applicationController.isUserLoggedIn();
        menuBarLogin.setDisable(userLoggedIn);
        menuBarLogout.setDisable(!userLoggedIn);
        menuBarSettings.setDisable(!userLoggedIn);


        //load all images of furnitures with rebates in the allSalesImages list
        HashMap<Integer, HashMap<Integer, List<Image>>> allFurnituresImages = applicationController.getAllFurnituresImages();
        for(HashMap<Integer, List<Image>> hashMap : allFurnituresImages.values()){
            for(List<Image> images : hashMap.values()){
                for(Image image : images){
                    if(applicationController.getFurnitureByImage(image).getRebate() != 0) {
                        allSalesImages.add(image);
                    }
                }
            }
        }

        //load all images of categories in allCategoryImages list
        allCategoryImages.addAll(applicationController.getAllCategoryImages());

        showSalesImages();
        showCategoryImages();
    }

    /**
     * Adjusts the images in the image views showing the previous image in the list
     */
    public void homeSalesSliderLeftOCE() { //slides the sales images of the slider to the left
        if(counterSales > 0){
            counterSales-- ;
            showSalesImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the next image in the list
     */
    public void homeSalesSliderRightOCE() { //slides the sales images of the slider to the right
        if(counterSales < allSalesImages.size() - allSalesImageViews.size()){
            counterSales++ ;
            showSalesImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the previous image in the list
     */
    public void homeCategoriesSliderLeftOCE() { //slides the category images of the slider to the left
        if(counterCategories > 0){
            counterCategories--;
            showCategoryImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the next image in the list
     */
    public void homeCategoriesSliderRightOCE() { //slides the category images of the slider to the right
        if(counterCategories < allCategoryImages.size() - allCategoryImageViews.size()){
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
    public void openSingleView(MouseEvent mouseEvent) throws Exception {
        applicationController.openSingleView(allSalesImages.get(Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId())), mainAnchorPane);
    }

    public void openCategory(MouseEvent mouseEvent) throws Exception {
        applicationController.openCategory(allCategoryImages.get(Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId())), mainAnchorPane);
    }

    public void openLogin() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void openSettings() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    public void openShoppingCart() throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    public void logout() {
    }

    /**
     * Fills all furniture sales image views with furniture sales images
     * Disables the unused furniture sales image views
     */
    public void showSalesImages(){
        for(int i = 0; i < allSalesImageViews.size() && i < allSalesImages.size(); i++){
            allSalesImageViews.get(i).setImage(allSalesImages.get(counterSales + i));
            allSalesImageViews.get(i).setId(String.valueOf(counterSales + i));
        }
        for(int i = allSalesImageViews.size() - 1; i > allSalesImages.size() - 1; i--){
            allSalesImageViews.get(i).setDisable(true);
        }
    }

    /**
     * Fills all category image views with category images
     * Disables the unused category image views
     */
    public void showCategoryImages(){
        for(int i = 0; i < allCategoryImageViews.size() && i < allCategoryImages.size(); i++){
            allCategoryImageViews.get(i).setImage(allCategoryImages.get(counterCategories + i));
            allCategoryImageViews.get(i).setId(String.valueOf(counterCategories + i));
        }
        for(int i = allCategoryImageViews.size() - 1; i > allCategoryImages.size() - 1; i--){
            allCategoryImageViews.get(i).setDisable(true);
        }
    }

    public void showInformationPane(MouseEvent mouseEvent) {
        //TO BE IMPLEMENTED
        //show new temporary pane when hovering over an furniture
    }

    public void suppressInformationPane(MouseEvent mouseEvent) {
        //TO BE IMPLEMENTED
        //show new temporary pane when hovering over an furniture
    }
}