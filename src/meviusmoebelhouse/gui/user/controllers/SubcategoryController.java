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
    ArrayList<ImageView> allFurnitureImageViews = new ArrayList<>();

    //List with all furniture images in this subcategory
    List<Furniture> allFurnitures = new ArrayList<>();

    public SubcategoryController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFurnitureImageViews.addAll(Arrays.asList(categoriesFurnituresImage1, categoriesFurnituresImage2,
                categoriesFurnituresImage3, categoriesFurnituresImage4, categoriesFurnituresImage5,
                categoriesFurnituresImage6, categoriesFurnituresImage7, categoriesFurnituresImage8,
                categoriesFurnituresImage9, categoriesFurnituresImage10, categoriesFurnituresImage11,
                categoriesFurnituresImage12));

        updateUiBasedOnLoginState();

        currentSubcategory = applicationController.getCurrentSubcategory();

        subcategoryLabel.setText(currentSubcategory.getName());
        backToCategoryButton.setText(applicationController.getCurrentCategory().getName());

        HashMap<Subcategory, List<Furniture>> allFurnituresBySubcategory =
                applicationController.getAllFurnituresBySubcategory();

        //load all images of furnitures with rebates of this category in the allSalesImages list and all
        //furnitures without rebates in the allFurnitureImages list
        //early database might not have entries for every cat.
        for(Furniture furniture : allFurnituresBySubcategory.get(currentSubcategory)){
            allFurnitures.add(furniture);
        }
        showFurnitureImages();
    }

    public void backToHomeOCE(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Home");
    }

    public void openLogin(ActionEvent actionEvent) throws Exception {
        applicationController.switchScene(mainAnchorPane, "Login");
    }

    public void logout() throws Exception {
        applicationController.logout(mainAnchorPane);
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
    public void openSingleView(MouseEvent mouseEvent) throws Exception {
        int idOfFurniture = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
        applicationController.openSingleView(idOfFurniture, mainAnchorPane);
    }

    /**
     * Fills all furniture image views with furniture images
     * Disables the unused furniture image views
     */
    public void showFurnitureImages(){
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
}
