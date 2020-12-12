package meviusmoebelhouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import meviusmoebelhouse.gui.ApplicationController;
import meviusmoebelhouse.gui.user.fxmlfiles.FXML;
import meviusmoebelhouse.gui.user.controllers.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationController applicationController = new ApplicationController();

        FXMLLoader loader = new FXMLLoader(FXML.class.getResource("Home.fxml"));
        loader.setControllerFactory(c -> new HomeController(applicationController));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
