package client.waitingRoom.controller;

import javafx.event.ActionEvent;
import support.helpers.SceneSwitcher;

public class WaitingRoomController {
    public void onCancel(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }
}
