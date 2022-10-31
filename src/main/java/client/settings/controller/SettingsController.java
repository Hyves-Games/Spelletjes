package client.settings.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;
import support.helpers.SceneSwitcher;

public class SettingsController {

    @FXML private TextField ip;
    @FXML private TextField port;

    public void onBackClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }

    public void onConnectClick() {
        System.out.println(ip.getText());
        System.out.println(port.getText());
    }


}
