package meviusmoebelhouse.gui.user.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Category;

import java.net.URL;
import java.util.*;

public class CategoryController implements Initializable {

    private ApplicationController applicationController = null;

    public AnchorPane mainAnchorPane;

    public int counterSales = 0;            //Index of which first furniture in sales is showing (0/1/2/3/4/5...)
    public int counterFurnitures = 0;       //Index of which first furniture in furnitures is showing (0/1/2/3/4/5...)
    public int counterSubcategories = 0;    //Index of which first category in categories is showing (0/1/2/3/4/5...)

    public Label categoryLabel;

    public Category currentCategory;

    public ImageView    categoriesSalesImage1, categoriesSalesImage2,
            categoriesSalesImage3, categoriesSalesImage4,
            categoriesFurnituresImage1, categoriesFurnituresImage2,
            categoriesFurnituresImage3, categoriesFurnituresImage4,
            categoriesSubcategoriesImage1, categoriesSubcategoriesImage2,
            categoriesSubcategoriesImage3, categoriesSubcategoriesImage4;

    ArrayList<ImageView> allSalesImageViews = new ArrayList<>(); //List with all categorySalesImageViews
    ArrayList<ImageView> allFurnitureImageViews = new ArrayList<>(); //List with all categoryFurnitureImageViews
    ArrayList<ImageView> allSubcategoryImageViews = new ArrayList<>(); //List with all categorySubcategoryImageViews

    ArrayList<Image> allSalesImages = new ArrayList<>(); //List with all sales images in this category
    ArrayList<Image> allFurnitureImages = new ArrayList<>(); //List with all furniture images in this category
    ArrayList<Image> allSubcategoryImages = new ArrayList<>(); //List with all subcategory images in this category

    public CategoryController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentCategory = applicationController.getCurrentCategory();

        categoryLabel.setText(currentCategory.getName());

        allSalesImageViews.addAll(Arrays.asList(        categoriesSalesImage1, categoriesSalesImage2,
                categoriesSalesImage3, categoriesSalesImage4));
        allFurnitureImageViews.addAll(Arrays.asList(    categoriesFurnituresImage1, categoriesFurnituresImage2,
                categoriesFurnituresImage3, categoriesFurnituresImage4));
        allSubcategoryImageViews.addAll(Arrays.asList(  categoriesSubcategoriesImage1, categoriesSubcategoriesImage2,
                categoriesSubcategoriesImage3, categoriesSubcategoriesImage4));

        HashMap<Integer, HashMap<Integer, List<Image>>> allFurnituresImages = applicationController.getAllFurnituresImages();
        //load all images of furnitures with rebates of this category in the allSalesImages list and all
        //furnitures without rebates in the allFurnitureImages list
        if(allFurnituresImages.containsKey(currentCategory.getIdCategory())){//early database might not have entries for every cat./subCat.
            for(List<Image> list : allFurnituresImages.get(currentCategory.getIdCategory()).values()){
                for(Image image : list){
                    if(applicationController.getFurnitureByImage(image).getRebate() == 0){
                        allFurnitureImages.add(image);
                    } else{
                        allSalesImages.add(image);
                    }
                }
            }
            //load all images of subcategories of this category in allSubcategoryImages list
            allSubcategoryImages.addAll(applicationController.getAllSubcategoryImages().get(currentCategory.getIdCategory()));
        }
        //show all images in the scene/frame
        showSalesImages();
        showFurnitureImages();
        showSubcategoryImages();
    }

    public void backToHomeOCE() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void openLogin() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void logout() {
    }

    public void openSettings() throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    public void openShoppingCart() throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    public void categorySalesSliderLeftOCE() {
        if(counterSales > 0){
            counterSales--;
            showSalesImages();
        }
    }

    public void categorySalesSliderRightOCE() {
        if(counterSales < allSalesImages.size() - allSalesImageViews.size()){
            counterSales++;
            showSalesImages();
        }
    }

    public void categoryFurnituresSliderLeftOCE() {
        if(counterFurnitures > 0){
            counterFurnitures--;
            showFurnitureImages();
        }
    }

    public void categoryFurnituresSliderRightOCE() {
        if(counterFurnitures < allFurnitureImages.size() - allFurnitureImageViews.size()){
            counterFurnitures++;
            showFurnitureImages();
        }
    }

    public void categoriesSubcategoriesSliderLeftOCE() {
        if(counterSubcategories > 0){
            counterSubcategories--;
            showSubcategoryImages();
        }
    }

    public void categoriesSubcategoriesSliderRightOCE() {
        if(counterSubcategories < allSubcategoryImages.size() - allSubcategoryImageViews.size()){
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
    public void openSingleView(MouseEvent mouseEvent) throws Exception {
        if(allSalesImageViews.contains((ImageView) mouseEvent.getSource())){
            applicationController.openSingleView(allSalesImages.get(Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId())), mainAnchorPane);
        } else{
            applicationController.openSingleView(allFurnitureImages.get(Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId())), mainAnchorPane);
        }
    }


    public void openSubcategory(MouseEvent mouseEvent) throws Exception {
        applicationController.openSubcategory(allSubcategoryImages.get(Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId())), mainAnchorPane);
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
            //allSalesImageViews.get(i).setDisable(true);
            allSalesImageViews.get(i).setVisible(false);
        }
    }

    /**
     * Fills all furniture image views with furniture images
     * Disables the unused furniture image views
     */
    public void showFurnitureImages(){
        for(int i = 0; i < allFurnitureImageViews.size() && i < allFurnitureImages.size(); i++){
            allFurnitureImageViews.get(i).setImage(allFurnitureImages.get(counterFurnitures + i));
            allFurnitureImageViews.get(i).setId(String.valueOf(counterFurnitures + i));
        }
        for(int i = allFurnitureImageViews.size() - 1; i > allFurnitureImages.size() - 1; i--){
            //allFurnitureImageViews.get(i).setDisable(true);
            allFurnitureImageViews.get(i).setVisible(false);
        }
    }

    /**
     * Fills all subcategory image views with subcategory images
     * Disables the unused subcategory image views
     */
    public void showSubcategoryImages(){
        for(int i = 0; i < allSubcategoryImageViews.size() && i < allSubcategoryImages.size(); i++){
            allSubcategoryImageViews.get(i).setImage(allSubcategoryImages.get(counterSubcategories + i));
            allSubcategoryImageViews.get(i).setId(String.valueOf(counterSubcategories + i));
        }
        for(int i = allSubcategoryImageViews.size() - 1; i > allSubcategoryImages.size() - 1; i--){
            //allSubcategoryImageViews.get(i).setDisable(true);
            allSubcategoryImageViews.get(i).setVisible(false);
        }
    }
}
