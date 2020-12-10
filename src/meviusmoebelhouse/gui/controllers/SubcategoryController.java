package meviusmoebelhouse.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Subcategory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SubcategoryController implements Initializable {

    public AnchorPane mainAnchorPane;

    private ApplicationController applicationController = null;

    //Index of which first furniture in furnitures is showing (0/1/2/3/4/5...)
    public int counterFurnitures = 0;

    public Subcategory currentSubcategory;

    public Label subcategoryLabel;
    public Button backToCategoryButton;

    public ImageView    categoriesFurnituresImage1, categoriesFurnituresImage2, categoriesFurnituresImage3,
            categoriesFurnituresImage4, categoriesFurnituresImage5, categoriesFurnituresImage6,
            categoriesFurnituresImage7, categoriesFurnituresImage8, categoriesFurnituresImage9,
            categoriesFurnituresImage10,categoriesFurnituresImage11,categoriesFurnituresImage12;

    //List with all subcategoryFurnitureImageViews
    ArrayList<ImageView> allFurnitureImageViews = new ArrayList<>();

    //List with all furniture images in this subcategory
    ArrayList<Image> allFurnitureImages = new ArrayList<>();

    public SubcategoryController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentSubcategory = applicationController.getCurrentSubcategory();

        subcategoryLabel.setText(currentSubcategory.getName());
        backToCategoryButton.setText(applicationController.getCurrentCategory().getName());

        allFurnitureImageViews.addAll(Arrays.asList(categoriesFurnituresImage1, categoriesFurnituresImage2,
                categoriesFurnituresImage3, categoriesFurnituresImage4, categoriesFurnituresImage5,
                categoriesFurnituresImage6, categoriesFurnituresImage7, categoriesFurnituresImage8,
                categoriesFurnituresImage9, categoriesFurnituresImage10, categoriesFurnituresImage11,
                categoriesFurnituresImage12));

        HashMap<Integer, HashMap<Integer, List<Image>>> allFurnituresImages = applicationController.getAllFurnituresImages();
        //load all images of furnitures with rebates of this category in the allSalesImages list and all
        //furnitures without rebates in the allFurnitureImages list
        //early database might not have entries for every cat.
        if(allFurnituresImages.containsKey(currentSubcategory.getIdCategory())) {
            //early database might not have entries for every subCat.
            if(allFurnituresImages.get(currentSubcategory.getIdCategory()).containsKey(currentSubcategory.getIdSubcategory())){
                for (List<Image> list : allFurnituresImages.get(currentSubcategory.getIdCategory()).values()) {
                    allFurnitureImages.addAll(list);
                }
            }
        }
        showFurnitureImages();
    }

    public void backToHomeOCE(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void openLogin(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void logout(ActionEvent actionEvent) {
    }

    public void openSettings(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    public void openShoppingCart(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    public void backToCategoryOCE(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Category");
    }

    /**
     * Adjusts the images in the image views showing the previous image in the list
     * @param actionEvent actionEvent
     */
    public void subcategoryFurnituresSliderLeftOCE(ActionEvent actionEvent) {
        if(counterFurnitures > 0){
            counterFurnitures--;
            showFurnitureImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the next image in the list
     * @param actionEvent actionEvent
     */
    public void subcategoryFurnituresSliderRightOCE(ActionEvent actionEvent) {
        if(counterFurnitures < allFurnitureImages.size() - allFurnitureImageViews.size()){
            counterFurnitures++;
            showFurnitureImages();
        }
    }

    /**
     * Passes the Image that got clicked to the Application controller which opens the single item view of
     * the furnitures image clicked
     * @param mouseEvent mouseEvent
     * @throws Exception exception
     */
    public void openSingleView(MouseEvent mouseEvent) throws Exception {
        applicationController.openSingleView(allFurnitureImages.get(Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId())), mainAnchorPane);
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
            allFurnitureImageViews.get(i).setDisable(true);
        }
    }
}
