package client.game.controller;

import javafx.event.ActionEvent;
import support.helpers.SceneSwitcher;

public class GameController {
    public void onBackClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }
}
