package meviusmoebelhouse.gui.controllers;

import javafx.fxml.Initializable;
import meviusmoebelhouse.Main;
import meviusmoebelhouse.gui.ApplicationController;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    ApplicationController applicationController = null;

    public SettingsController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
