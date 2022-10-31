package client.menu.controller;

import javafx.event.ActionEvent;
import support.helpers.SceneSwitcher;


public class MenuController {

    public void onPlayGameClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "game/game.fxml", "Waiting room");
    }

    public void onSettingsClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "settings/settings.fxml", "Settings");
    }
}
