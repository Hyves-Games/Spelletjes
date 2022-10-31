package client.settings.controller;

import javafx.event.ActionEvent;
import support.helpers.SceneSwitcher;

public class SettingsController {
    public void onBackClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }
}
