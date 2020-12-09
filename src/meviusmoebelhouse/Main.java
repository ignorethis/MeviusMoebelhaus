package meviusmoebelhouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.gui.fxmlfiles.FXML;

public class Main extends Application {

    public static ApplicationController applicationController = new ApplicationController();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = (Parent) FXMLLoader.load(FXML.class.getResource("Home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ApplicationController getApplicationController(){
        return applicationController;
    }

}
