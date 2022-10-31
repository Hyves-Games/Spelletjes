package client.menu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import support.helpers.SceneSwitcher;

public class MenuController
{

    @FXML Label serverStatus;

    public void onPlayGameClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "waitingRoom/waitingroom.fxml", "Waiting room");
    }

    public void onSettingsClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "settings/settings.fxml", "Settings");
    }

}
