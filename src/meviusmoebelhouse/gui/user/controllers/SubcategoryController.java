package meviusmoebelhouse.gui.user.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.model.Furniture;
import meviusmoebelhouse.model.Subcategory;

import java.net.URL;
import java.util.*;

public class SubcategoryController implements Initializable {

    @FXML private AnchorPane mainAnchorPane;
    @FXML private Button  subcategoryFurnituresSliderLeftButton, subcategoryFurnituresSliderRightButton,
                    menuBarLogin, menuBarLogout, menuBarSettings;

    @FXML private Label subcategoryLabel;
    @FXML private Button backToCategoryButton;

    @FXML private ImageView    categoriesFurnituresImage1, categoriesFurnituresImage2, categoriesFurnituresImage3,
            categoriesFurnituresImage4, categoriesFurnituresImage5, categoriesFurnituresImage6,
            categoriesFurnituresImage7, categoriesFurnituresImage8, categoriesFurnituresImage9,
            categoriesFurnituresImage10,categoriesFurnituresImage11,categoriesFurnituresImage12;

    private ApplicationController applicationController = null;

    //Index of which first furniture in furnitures is showing (0/1/2/3/4/5...)
    private int counterFurnitures = 0;

    private Subcategory currentSubcategory;

    //List with all subcategoryFurnitureImageViews
    private ArrayList<ImageView> allFurnitureImageViews = new ArrayList<>();

    //List with all furniture images in this subcategory
    private List<Furniture> allFurnitures = new ArrayList<>();


    public SubcategoryController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentSubcategory = applicationController.getCurrentSubcategory();

        fillImageViewListsWithAllImageViews();

        fillFurnitureListFromSubcategory();

        showFurnitureImages();

        updateUiBasedOnLoginState();

        updateButtonsAndLabels();
    }


    //ALL FUNCTIONS ACCESSED BY THE FXML BUTTONS

    @FXML private void backToHomeOCE(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    @FXML private void openLogin(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    @FXML private void logout() throws Exception {
        applicationController.logout(mainAnchorPane);
    }

    @FXML private void openSettings(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Settings");
    }

    @FXML private void openShoppingCart(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "ShoppingCart");
    }

    @FXML private void backToCategoryOCE(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Category");
    }

    /**
     * Adjusts the images in the image views showing the previous image in the list
     * @param actionEvent actionEvent
     */
    @FXML private void subcategoryFurnituresSliderLeftOCE(ActionEvent actionEvent) {
        if(counterFurnitures > 0){
            counterFurnitures--;
            showFurnitureImages();
        }
    }

    /**
     * Adjusts the images in the image views showing the next image in the list
     * @param actionEvent actionEvent
     */
    @FXML private void subcategoryFurnituresSliderRightOCE(ActionEvent actionEvent) {
        if(counterFurnitures < allFurnitures.size() - allFurnitureImageViews.size()){
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
    @FXML private void openSingleView(MouseEvent mouseEvent) throws Exception {
        int idOfFurniture = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
        applicationController.openSingleView(idOfFurniture, mainAnchorPane);
    }



    //HELPING FUNCTIONS

    /**
     * Fills all furniture image views with furniture images
     * Disables the unused furniture image views
     */
    private void showFurnitureImages(){
        for(int i = 0; i < allFurnitureImageViews.size() && i < allFurnitures.size(); i++){
            allFurnitureImageViews.get(i).setImage(allFurnitures
                    .get(counterFurnitures + i).getImage());
            allFurnitureImageViews.get(i).setId(String.valueOf(allFurnitures
                    .get(counterFurnitures + i).getIdFurniture()));
        }
        //disable all unused image views
        for(int i = allFurnitureImageViews.size() - 1; i > allFurnitures.size() - 1; i--){
            allFurnitureImageViews.get(i).setDisable(true);
        }
        //Dis/Enable Buttons of furnitures if it is negative or too big
        subcategoryFurnituresSliderRightButton.setDisable(counterFurnitures >= allFurnitures.size() - 12);
        subcategoryFurnituresSliderLeftButton.setDisable(counterFurnitures <= 0);
    }

    private void updateUiBasedOnLoginState() {
        boolean userLoggedIn = applicationController.isUserLoggedIn();
        menuBarLogin.setDisable(userLoggedIn);
        menuBarLogout.setDisable(!userLoggedIn);
        menuBarSettings.setDisable(!userLoggedIn);
    }

    private void updateButtonsAndLabels() {
        subcategoryLabel.setText(currentSubcategory.getName());
        backToCategoryButton.setText(applicationController.getCurrentCategory().getName());
    }

    private void fillFurnitureListFromSubcategory() {
        for(Furniture furniture : applicationController.getAllFurnituresBySubcategory().get(currentSubcategory)){
            allFurnitures.add(furniture);
        }
    }

    private void fillImageViewListsWithAllImageViews() {
        allFurnitureImageViews.addAll(Arrays.asList(categoriesFurnituresImage1, categoriesFurnituresImage2,
                categoriesFurnituresImage3, categoriesFurnituresImage4, categoriesFurnituresImage5,
                categoriesFurnituresImage6, categoriesFurnituresImage7, categoriesFurnituresImage8,
                categoriesFurnituresImage9, categoriesFurnituresImage10, categoriesFurnituresImage11,
                categoriesFurnituresImage12));
    }
}
