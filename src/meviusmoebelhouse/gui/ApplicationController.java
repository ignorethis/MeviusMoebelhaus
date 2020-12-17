package meviusmoebelhouse.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import meviusmoebelhouse.gui.admin.controllers.*;
import meviusmoebelhouse.gui.admin.fxmlfiles.FXMLADMIN;
import meviusmoebelhouse.gui.user.controllers.*;
import meviusmoebelhouse.gui.user.fxmlfiles.FXMLFinder;
import meviusmoebelhouse.model.*;
import meviusmoebelhouse.repositories.*;
import meviusmoebelhouse.viewmodel.ShoppingCart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class ApplicationController {
    private Category    currentCategory;
    private Subcategory currentSubcategory;
    private Furniture   currentFurniture;
    private User        currentUser;
    private User        currentUserToChange = null;
    private ShoppingCart shoppingCart;

    private List<Category>          allCategories;
    private List<Customer>          allCustomers;
    private List<Furniture>         allFurnitures;
    private List<Invoice>           allInvoices;
    private List<InvoiceDetails>    allInvoiceDetails;
    private List<Staff>             allStaffs;
    private List<Subcategory>       allSubcategories;
    private List<User>              allUsers;
    private List<UserRole>          allUserRoles;

    private ArrayList<Furniture>    newShoppingcart = new ArrayList<>();

    //new structure:
    //HashMap<SubcategoryId, List<Furnitures>
    private HashMap<Subcategory, List<Furniture>>   allFurnituresBySubcategory = new HashMap<>();

    //repositories
    private CategoryRepository categoryRepository = null;
    private CustomerRepository customerRepository = null;
    private FurnitureRepository furnitureRepository = null;
    private InvoiceRepository invoiceRepository = null;
    private InvoiceDetailsRepository invoiceDetailsRepository = null;
    private StaffRepository staffRepository = null;
    private SubcategoryRepository subcategoryRepository = null;
    private UserRepository userRepository = null;
    private UserRoleRepository userRoleRepository = null;

    private String pathToWarehouseOut = String.valueOf(
            getClass().getProtectionDomain().getCodeSource().getLocation())
            .replace("/", "\\").substring(6);


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
            allUsers = userRepository.getAll();

            userRoleRepository = new UserRoleRepository(conn);
            allUserRoles = userRoleRepository.getAll();

            shoppingCart = new ShoppingCart();

            /////////////////////new structure
            fillAllFurnituresBySubcategory();

            //set all furnitures image
            setAllFurnituresImage();

            //set all categories image
            setAllCategoriesImage();

            //set all subcategories image
            setAllSubcategoriesImage();

            System.out.println("Test");
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
            loader = new FXMLLoader(FXMLFinder.class.getResource(fxmlName + ".fxml"));
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
            case "Register":
                loader.setControllerFactory(c -> new RegisterController(this));
                break;
            case "AdminHome":
                loader.setControllerFactory(c -> new AdminHomeController(this));
                break;
            case "AdminAccountManager":
                loader.setControllerFactory(c -> new AdminAccountManagerController(this));
                break;
            case "AdminAccountEdit":
                loader.setControllerFactory(c -> new AdminAccountEditController(this));
                break;
            case "AdminFurnitureManager":
                loader.setControllerFactory(c -> new AdminFurnitureManagerController(this));
                break;
            case "AdminAccountAdd":
                loader.setControllerFactory(c -> new AdminAccountAddController(this));
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

    public ShoppingCart getShoppingCart() { return shoppingCart; }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void setCurrentSubcategory(Subcategory currentSubcategory) {
        this.currentSubcategory = currentSubcategory;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
    }

    public List<Category> getAllCategories(){
        return allCategories;
    }

    /**
     * Function that is called when a click on a furniture image is done, sets the new current
     * values (furniture/category/subcategory) and switches the scene
     * @param imageOfFurniture image that got clicked
     * @param anchorPane anchorPane of the frame the image layed on
     * @throws Exception e
     */
    public void openSingleView(int idOfFurniture, AnchorPane anchorPane) throws Exception {
        currentFurniture = getFurnitureById(idOfFurniture);

        currentSubcategory = getSubcategoryById(currentFurniture.getIdSubcategory());

        currentCategory = getCategoryById(currentSubcategory.getIdCategory());

        switchScene(anchorPane, "Singleitem");
    }

    /**
     * Function that is called when a click on a category image is done, sets the new current
     * values (furniture/category/subcategory) and switches the scene
     * @param imageOfCategory image that got clicked
     * @param anchorPane anchorPane of the frame the image layed on
     * @throws Exception e
     */
    public void openCategory(int idOfCategory, AnchorPane anchorPane) throws Exception {
        currentCategory = getCategoryById(idOfCategory);

        currentSubcategory = null;

        currentFurniture = null;

        switchScene(anchorPane, "Category");
    }

    /**
     * Function that is called when a click on a subcategory image is done, sets the new current
     * values (furniture/category/subcategory) and switches the scene
     * @param imageOfSubcategory image that got clicked
     * @param anchorPane anchorPane of the frame the image layed on
     * @throws IOException e
     */
    public void openSubcategory(int idOfSubcategory, AnchorPane anchorPane) throws Exception {
        currentSubcategory = getSubcategoryById(idOfSubcategory);

        currentCategory = getCategoryById(currentSubcategory.getIdCategory());

        currentFurniture = null;

        switchScene(anchorPane, "Subcategory");
    }

    /**
     * Calculates the path of a given furniture and returns its image
     * @param f given furniture
     * @return image of given furniture
     */
    public Image getFurnitureImageByFurniture(Furniture f){
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/images/furniture/"
                + f.getIdFurniture() + "/" + f.getIdFurniture() + ".png");
            return new Image(inputStream);
        } catch (Exception e){
            return new Image(this.getClass().getResourceAsStream("/images/default_pic.png"));
        }
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

    public void addFurnitureToShoppingCart(int amount) {
        //add currentFurniture amount times in the shopping cart of the customer
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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

    public Staff getStaffById(int id){
        return allStaffs.stream().filter(s -> s.getIdStaff() == id).findFirst().orElse(null);
    }

    public ArrayList<Furniture> getNewShoppingcart() {
        return newShoppingcart;
    }

    public void addFurnitureToShoppingCart(Furniture f){
        newShoppingcart.add(f);
    }

    public User getCurrentUserToChange() {
        return currentUserToChange;
    }

    public void openAccountManagerEdit(AnchorPane mainAnchorPane, User temp) throws Exception {
        currentUserToChange = temp;
        switchScene(mainAnchorPane, "AdminAccountEdit");
    }

    public Customer getCustomerById(int id){
        return allCustomers.stream().filter(s -> s.getIdCustomer() == id).findFirst().orElse(null);
    }

    public Customer getCustomerByUserId(int id){
        return allCustomers.stream().filter(s -> s.getIdUser() == id).findFirst().orElse(null);
    }

    public int getUserRoleByUserId(int id) {
        return (allUsers.stream().filter(s -> s.getIdUser() == id).findFirst().orElse(null))
                .getIdUserRole();
    }

    public User getUserByUserId(int id){
        return (allUsers.stream().filter(s -> s.getIdUser() == id).findFirst().orElse(null));
    }

    public void logout(AnchorPane anchorPane) throws Exception {
        currentUser = null;
        switchScene(anchorPane, "Home");
    }

    public void addFurnitureToDatabase(Furniture furniture) throws Exception {
        furnitureRepository.create(furniture);
        allFurnitures = furnitureRepository.getAll();
        fillAllFurnituresBySubcategory();
        setAllFurnituresImage();
    }

    public Staff getStaffByUserId(int id) {
        return allStaffs.stream().filter(s -> s.getIdUser() == id).findFirst().orElse(null);
    }

    public List<String> getAllCategoryNames(){
        List<String> strings = new ArrayList<>();

        for(Category c : allCategories){
            strings.add(c.getName());
        }

        return strings;
    }

    public List<String> getAllSubcategoryNamesOfCategory(String categoryName){
        List<String> strings = new ArrayList<>();
        int categoryId = getCategoryByName(categoryName).getIdCategory();

        for(Subcategory s : allSubcategories){
            if(s.getIdCategory() == categoryId)
            strings.add(s.getName());
        }

        return strings;
    }

    public Category getCategoryByName(String categoryName){
        return allCategories.stream().filter(s -> s.getName().equals(categoryName)).findFirst().orElse(null);
    }

    public void addNewUserToDatabase(User newUser) throws Exception {
        userRepository.create(newUser);
        allUsers = userRepository.getAll();
    }

    public void changeUserInDatabase(User currentUser) throws Exception {
        userRepository.update(currentUser);
        allUsers = userRepository.getAll();
    }

    public void addNewStaffToDatabase(Staff newStaff) throws Exception {
        staffRepository.create(newStaff);
        allStaffs = staffRepository.getAll();
    }

    public void addNewCustomerToDatabase(Customer newCustomer) throws Exception {
        customerRepository.create(newCustomer);
        allCustomers = customerRepository.getAll();
    }

    public int getUserByUsername(String username) {
        return Objects.requireNonNull(allUsers.stream().filter(
                s -> s.getUsername().equals(username)).findFirst().orElse(null)).getIdUser();
    }

    public Subcategory getSubcategoryByName(String subcategoryName) {
        return  allSubcategories.stream().filter(s -> s.getName().equals(subcategoryName)).findFirst().orElse(null);
    }

    public void changeFurnitureInDatabase(Furniture currentFurniture) throws Exception {
        furnitureRepository.update(currentFurniture);
        allFurnitures = furnitureRepository.getAll();
    }

    public void changeStaffInDatabase(Staff currentStaff) throws Exception {
        staffRepository.update(currentStaff);
        allStaffs = staffRepository.getAll();
    }

    public void changeCustomerInDatabase(Customer currentCustomer) throws Exception {
        customerRepository.update(currentCustomer);
        allCustomers = customerRepository.getAll();
    }

    private void fillAllFurnituresBySubcategory(){
        allFurnituresBySubcategory = new HashMap<>();

        for(Furniture furniture : allFurnitures){
            Subcategory subcategoryOfFurniture = getSubcategoryById(furniture.getIdSubcategory());
            if(allFurnituresBySubcategory.containsKey(subcategoryOfFurniture)){
                //subcategory already exists with list
                allFurnituresBySubcategory.get(subcategoryOfFurniture).add(furniture);
            } else{
                //subcategory does not exist with list
                List<Furniture> list = new ArrayList<>();
                list.add(furniture);
                allFurnituresBySubcategory.put(subcategoryOfFurniture, list);
            }
        }
    }

    public HashMap<Subcategory, List<Furniture>> getAllFurnituresBySubcategory() {
        return allFurnituresBySubcategory;
    }

    private void setAllFurnituresImage() {
        String furnitureImagesDirectory = pathToWarehouseOut + "images\\furniture\\";

        for(Furniture furniture : allFurnitures){
            if(furniture.getImage() == null){
                Image newImage;
                //check if directory exist for furniture
                String path = furnitureImagesDirectory + furniture.getIdFurniture();
                File pathAsFile = new File(path);

                String pathToImage = path + "\\" + furniture.getIdFurniture() + ".png";

                if(!Files.exists(Paths.get(path))){

                    pathAsFile.mkdir();

                    Path source = Paths.get(pathToWarehouseOut + "images\\default_pic.png");
                    Path dest = Paths.get(path + "\\" + furniture.getIdFurniture() + ".png");
                    try {
                        Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(!Files.exists(Paths.get(pathToImage))){
                    Path source = Paths.get(pathToWarehouseOut + "images\\default_pic.png");
                    Path dest = Paths.get(path + "\\" + furniture.getIdFurniture() + ".png");
                    try {
                        Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                furniture.setImage(new Image("file:/" + pathToImage));
            }
        }
    }

    private void setAllCategoriesImage() {
        String categoryImagesDirectory = pathToWarehouseOut + "images\\category\\";

        for(Category category : allCategories){
            if(category.getImage() == null){
                Image newImage;
                //check if directory exist for furniture
                String path = categoryImagesDirectory + category.getIdCategory();
                File pathAsFile = new File(path);

                String pathToImage = path + "\\" + category.getIdCategory() + ".png";

                if(!Files.exists(Paths.get(path))){

                    pathAsFile.mkdir();

                    Path source = Paths.get(pathToWarehouseOut + "images\\default_pic.png");
                    Path dest = Paths.get(path + "\\" + category.getIdCategory() + ".png");
                    try {
                        Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(!Files.exists(Paths.get(pathToImage))){
                    Path source = Paths.get(pathToWarehouseOut + "images\\default_pic.png");
                    Path dest = Paths.get(path + "\\" + category.getIdCategory() + ".png");
                    try {
                        Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                category.setImage(new Image("file:/" + pathToImage));
            }
        }
    }

    private void setAllSubcategoriesImage() {
        String subcategoryImagesDirectory = pathToWarehouseOut + "images\\subcategory\\";

        for(Subcategory subcategory : allSubcategories){
            if(subcategory.getImage() == null){
                Image newImage;
                //check if directory exist for furniture
                String path = subcategoryImagesDirectory + subcategory.getIdSubcategory();
                File pathAsFile = new File(path);

                String pathToImage = path + "\\" + subcategory.getIdSubcategory() + ".png";

                if(!Files.exists(Paths.get(path))){

                    pathAsFile.mkdir();

                    Path source = Paths.get(pathToWarehouseOut + "images\\default_pic.png");
                    Path dest = Paths.get(path + "\\" + subcategory.getIdSubcategory() + ".png");
                    try {
                        Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(!Files.exists(Paths.get(pathToImage))){
                    Path source = Paths.get(pathToWarehouseOut + "images\\default_pic.png");
                    Path dest = Paths.get(path + "\\" + subcategory.getIdSubcategory() + ".png");
                    try {
                        Files.copy(source, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subcategory.setImage(new Image("file:/" + pathToImage));
            }
        }
    }

    public User getUserToLogIn(String username, String password) {
        return  allUsers.stream().filter(s -> (s.getUsername().equals(username)) && (s.getPassword().equals(password)))
                .findFirst().orElse(null);
    }

    public void addInvoiceDetailsToDatabase(InvoiceDetails invoiceDetails) throws Exception {
        invoiceDetailsRepository.create(invoiceDetails);
    }

    public void addInvoiceToDatabase(Invoice invoice) throws Exception {
        invoiceRepository.create(invoice);
    }

    public void updateStaff(Staff staffDataOfUser) throws Exception {
        staffRepository.update(staffDataOfUser);
    }
}