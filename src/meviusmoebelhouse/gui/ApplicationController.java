package meviusmoebelhouse.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import meviusmoebelhouse.gui.admin.controllers.AdminAccountManagerController;
import meviusmoebelhouse.gui.admin.controllers.AdminHomeController;
import meviusmoebelhouse.gui.admin.fxmlfiles.FXMLADMIN;
import meviusmoebelhouse.gui.user.controllers.*;
import meviusmoebelhouse.gui.user.fxmlfiles.FXML;
import meviusmoebelhouse.model.*;
import meviusmoebelhouse.repositories.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class ApplicationController {
    Category    currentCategory;
    Subcategory currentSubcategory;
    Furniture   currentFurniture;
    User currentUser;

    List<Category>          allCategories;
    List<Customer>          allCustomers;
    List<Furniture>         allFurnitures;
    List<Invoice>           allInvoices;
    List<InvoiceDetails>    allInvoiceDetails;


    List<Staff>             allStaffs;
    List<Subcategory>       allSubcategories;

    //for easy access to the images and data in general in home/category/subcategory frames
    HashMap<Integer, HashMap<Integer, List<Image>>> allFurnituresImages = new HashMap<>(); //HashMap<Category.id, HashMap<Subcategory.id, List<Furnitures>>>
    HashMap<Integer, List<Image>>                   allSubcategoryImages = new HashMap<>(); //HashMap<CategoryID, List<Subcategory>
    List<Image>                                     allCategoryImages = new ArrayList<>(); //List of All Images for categories

    //to access the id of the furniture/category/subcategory when an image is clicked later on
    HashMap<Image, Integer> IdForFurnitureImages     = new HashMap<>(); //Hashmap that saves the ID of an furniture image as a value with the image as a key
    HashMap<Image, Integer> IdForCategoryImages      = new HashMap<>(); //Hashmap that saves the ID of an category image as a value with the image as a key
    HashMap<Image, Integer> IdForSubcategoryImages   = new HashMap<>(); //Hashmap that saves the ID of an subcategory image as a value with the image as a key

    //repositories
    CategoryRepository categoryRepository = null;
    CustomerRepository customerRepository = null;
    FurnitureRepository furnitureRepository = null;
    InvoiceRepository invoiceRepository = null;
    InvoiceDetailsRepository invoiceDetailsRepository = null;
    StaffRepository staffRepository = null;
    SubcategoryRepository subcategoryRepository = null;
    UserRepository userRepository = null;
    UserRoleRepository userRoleRepository = null;

    public ApplicationController() {

        try {
            Class.forName("org.sqlite.JDBC"); // in configurationsdatei
            Connection conn = DriverManager.getConnection("jdbc:sqlite:db\\MeviusMoebelhaus.db"); // (xml/json/ini) auslagern.

            //read data from the database and store it in the above structure
            categoryRepository = new CategoryRepository(conn);
            allCategories = categoryRepository.getAll();

            customerRepository = new CustomerRepository(conn);
            allCustomers = customerRepository.getAll();

            furnitureRepository = new FurnitureRepository(conn);
            allFurnitures = furnitureRepository.getAll();

            invoiceRepository = new InvoiceRepository(conn);
            allInvoices = invoiceRepository.getAll();

            invoiceDetailsRepository = new InvoiceDetailsRepository(conn);
            allInvoiceDetails = invoiceDetailsRepository.getAll();

            staffRepository = new StaffRepository(conn);
            allStaffs = staffRepository.getAll();

            subcategoryRepository = new SubcategoryRepository(conn);
            allSubcategories = subcategoryRepository.getAll();

            userRepository = new UserRepository(conn);

            userRoleRepository = new UserRoleRepository(conn);

            //iterates all furnitures and fills the allFurnituresImages Hashmap and the IdForFurnitureImages Hashmap
            for(Furniture f : allFurnitures){
                Image t;
                if(allFurnituresImages.containsKey(getSubcategoryById(f.getIdSubcategory()).getIdCategory())) {
                    //case when the hashmap has already entries in the category (at least 1)
                    if (allFurnituresImages.get(getSubcategoryById(f.getIdSubcategory()).getIdCategory()).containsKey(f.getIdSubcategory())) {
                        //case when the hashmap has already entries in the subcategory (at least 1)
                        //adds the next entry to the existing list
                        t = getFurnitureImageByFurniture(f);
                        allFurnituresImages.get(getSubcategoryById(f.getIdSubcategory()).getIdCategory()).get(f.getIdSubcategory()).add(t);
                    } else {
                        //case when the hashmap has no entry in the subcategory yet
                        //adds a new HashMap with the first entry
                        HashMap<Integer, List<Image>> h = new HashMap<>();
                        List<Image> i = new ArrayList<>();
                        t = getFurnitureImageByFurniture(f);
                        i.add(t);
                        h.put(f.getIdSubcategory(), i);
                        allFurnituresImages.put(getSubcategoryById(f.getIdSubcategory()).getIdCategory(), h);
                    }
                } else {
                    //case when the hashmap has no entry in the category yet
                    //adds a new hashmap with the first entry
                    HashMap<Integer, List<Image>> h = new HashMap<>();
                    List<Image> i = new ArrayList<>();
                    t = getFurnitureImageByFurniture(f);
                    i.add(t);
                    h.put(f.getIdSubcategory(), i);
                    allFurnituresImages.put(getSubcategoryById(f.getIdSubcategory()).getIdCategory(), h);
                }
                IdForFurnitureImages.put(t, f.getIdFurniture());
            }

            //iterates all subcategories and fills the allSubcategoriesImages Hashmap and the IdForSubcategoryImages Hashmap
            for(Subcategory s : allSubcategories){
                Image t;
                if(allSubcategoryImages.containsKey(s.getIdCategory())){
                    //case when the hashmap has already entries in the category (at least 1)
                    t = getSubcategoryImageBySubcategory(s);
                    allSubcategoryImages.get(s.getIdCategory()).add(t);
                } else {
                    //case when the hashmap has no entry yet in the category
                    List<Image> i = new ArrayList<>();
                    t = getSubcategoryImageBySubcategory(s);
                    i.add(t);
                    allSubcategoryImages.put(s.getIdCategory(), i);
                }
                IdForSubcategoryImages.put(t, s.getIdSubcategory());
            }

            //iterates all Categories and fills the allCategoriesImages Hashmap and the IdForCategoryImages Hashmap
            for(Category c: allCategories){
                Image t = getCategoryImageByCategory(c);
                allCategoryImages.add(t);
                IdForCategoryImages.put(t, c.getIdCategory());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that takes control of switching a scene/frame
     * @param anchorPane is the main anchor pane of the calling controller
     * @param fxmlName is the name of the requested new frame
     * @throws Exception e
     */
    public void switchScene(AnchorPane anchorPane, String fxmlName) throws Exception {
        FXMLLoader loader;
        if(fxmlName.startsWith("Admin")){
            loader = new FXMLLoader(FXMLADMIN.class.getResource(fxmlName + ".fxml"));
        } else{
            loader = new FXMLLoader(FXML.class.getResource(fxmlName + ".fxml"));
        }
        setControllerFactory(fxmlName, loader);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(fxmlName);
        stage.show();
    }

    public void setControllerFactory(String viewName, FXMLLoader loader) throws Exception {
        switch (viewName) {
            case "Category":
                loader.setControllerFactory(c -> new CategoryController(this));
                break;
            case "Home":
                loader.setControllerFactory(c -> new HomeController(this));
                break;
            case "Login":
                loader.setControllerFactory(c -> new LoginController(this));
                break;
            case "Settings":
                loader.setControllerFactory(c -> new SettingsController(this));
                break;
            case "ShoppingCart":
                loader.setControllerFactory(c -> new ShoppingCartController(this));
                break;
            case "Singleitem":
                loader.setControllerFactory(c -> new SingleItemController(this));
                break;
            case "Subcategory":
                loader.setControllerFactory(c -> new SubcategoryController(this));
                break;
            case "AdminHome":
                loader.setControllerFactory(c -> new AdminHomeController(this));
                break;
            case "AdminAccountManager":
                loader.setControllerFactory(c -> new AdminAccountManagerController(this));
                break;
            default:
                throw new Exception("Please add a controller factory for '" + viewName + "'");
        }
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public Subcategory getCurrentSubcategory() {
        return currentSubcategory;
    }

    public Furniture getCurrentFurniture() {
        return currentFurniture;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void setCurrentSubcategory(Subcategory currentSubcategory) {
        this.currentSubcategory = currentSubcategory;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
    }

    public HashMap<Integer, HashMap<Integer, List<Image>>> getAllFurnituresImages(){
        return allFurnituresImages;
    }

    public HashMap<Integer, List<Image>> getAllSubcategoryImages(){
        return allSubcategoryImages;
    }

    public List<Image> getAllCategoryImages(){
        return allCategoryImages;
    }

    /**
     * Function that is called when a click on a furniture image is done, sets the new current
     * values (furniture/category/subcategory) and switches the scene
     * @param imageOfFurniture image that got clicked
     * @param anchorPane anchorPane of the frame the image layed on
     * @throws Exception e
     */
    public void openSingleView(Image imageOfFurniture, AnchorPane anchorPane) throws Exception {
        int idFur = IdForFurnitureImages.get(imageOfFurniture);
        for(Furniture f : allFurnitures){
            if(f.getIdFurniture() == idFur){
                setCurrentFurniture(f);
            }
        }

        int idCat = getSubcategoryById(currentFurniture.getIdSubcategory()).getIdCategory();
        for(Category c : allCategories){
            if(c.getIdCategory() == idCat){
                setCurrentCategory(c);
            }
        }

        int idSub = currentFurniture.getIdSubcategory();
        for(Subcategory s : allSubcategories){
            if(s.getIdSubcategory() == idSub){
                setCurrentSubcategory(s);
            }
        }

        switchScene(anchorPane, "Singleitem");
    }

    /**
     * Function that is called when a click on a category image is done, sets the new current
     * values (furniture/category/subcategory) and switches the scene
     * @param imageOfCategory image that got clicked
     * @param anchorPane anchorPane of the frame the image layed on
     * @throws Exception e
     */
    public void openCategory(Image imageOfCategory, AnchorPane anchorPane) throws Exception {
        int idCat = IdForCategoryImages.get(imageOfCategory);

        for(Category c : allCategories){
            if(c.getIdCategory() == idCat){
                setCurrentCategory(c);
            }
        }

        currentFurniture = null;
        currentSubcategory = null;

        switchScene(anchorPane, "Category");
    }

    /**
     * Function that is called when a click on a subcategory image is done, sets the new current
     * values (furniture/category/subcategory) and switches the scene
     * @param imageOfSubcategory image that got clicked
     * @param anchorPane anchorPane of the frame the image layed on
     * @throws IOException e
     */
    public void openSubcategory(Image imageOfSubcategory, AnchorPane anchorPane) throws Exception {
        int idSub = IdForSubcategoryImages.get(imageOfSubcategory);

        for(Subcategory s : allSubcategories){
            if(s.getIdSubcategory() == idSub){
                setCurrentSubcategory(s);
            }
        }

        int idCat = currentSubcategory.getIdCategory();
        for(Category c : allCategories){
            if(c.getIdCategory() == idCat){
                setCurrentCategory(c);
            }
        }

        currentFurniture = null;

        switchScene(anchorPane, "Subcategory");
    }

    /**
     * Calculates the path of a given furniture and returns its image
     * @param f given furniture
     * @return image of given furniture
     */
    public Image getFurnitureImageByFurniture(Furniture f){
        return new Image(   this.getClass().getResourceAsStream("/images/furniture/"
                + f.getIdFurniture() + "/" + f.getIdFurniture() + ".png"));
    }

    /**
     * Calculates the path of a given category and returns its image
     * @param c given category
     * @return image of given category
     */
    public Image getCategoryImageByCategory(Category c){
        return new Image(   this.getClass().getResourceAsStream("/images/category/"
                + c.getIdCategory() + "/" + c.getIdCategory() + ".png"));
    }

    /**
     * Calculates the path of a given subcategory and returns its image
     * @param s given subcategory
     * @return image of given subcategory
     */
    public Image getSubcategoryImageBySubcategory(Subcategory s){
        return new Image(   this.getClass().getResourceAsStream("/images/subcategory/"
                + s.getIdSubcategory() + "/" + s.getIdSubcategory() + ".png"));
    }

    /**
     * Gets an id of a furniture, returns the furniture when found in allFurnitures list or null
     * @param id of furniture
     * @return furniture found
     */
    public Furniture getFurnitureById(int id){
        return allFurnitures.stream().filter(f -> f.getIdFurniture() == id).findFirst().orElse(null);
    }

    /**
     * Gets an id of a category, returns the category when found in allCategories list or null
     * @param id of category
     * @return category found
     */
    public Category getCategoryById(int id){
        return allCategories.stream().filter(c -> c.getIdCategory() == id).findFirst().orElse(null);
    }

    /**
     * Gets an id of a subcategory, returns the subcategory when found in allSubcategories list or null
     * @param id of subcategory
     * @return subcategory found
     */
    public Subcategory getSubcategoryById(int id){
        return allSubcategories.stream().filter(s -> s.getIdSubcategory() == id).findFirst().orElse(null);
    }

    /**
     * Gets the image of a furniture and returns the furniture itself
     * @param i image of the furniture
     * @return furniture itself
     */
    public Furniture getFurnitureByImage(Image i){
        return getFurnitureById(IdForFurnitureImages.get(i));
    }

    /**
     * Gets the image of a category and returns the category itself
     * @param i image of the category
     * @return category itself
     */
    public Category getCategoryByImage(Image i){
        return getCategoryById(IdForCategoryImages.get(i));
    }

    public void addFurnitureToShoppingCart(int amount) {
        //add currentFurniture amount times in the shopping cart of the customer
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public FurnitureRepository getFurnitureRepository() {
        return furnitureRepository;
    }

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    public InvoiceDetailsRepository getInvoiceDetailsRepository() {
        return invoiceDetailsRepository;
    }

    public StaffRepository getStaffRepository() {
        return staffRepository;
    }

    public SubcategoryRepository getSubcategoryRepository() {
        return subcategoryRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserRoleRepository getUserRoleRepository() {
        return userRoleRepository;
    }

    public boolean isUserLoggedIn(){
        return currentUser != null;
    }

    public List<Customer> getAllCustomers() {
        return allCustomers;
    }

    public List<Staff> getAllStaffs() {
        return allStaffs;
    }
}
