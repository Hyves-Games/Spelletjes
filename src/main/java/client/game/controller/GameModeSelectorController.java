package client.game.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class GameModeSelectorController {

    public void onBackClick() {
        SceneEnum.GAME_SELECTOR.switchTo();
    }

    public void onChooseGameMode(ActionEvent event) {
        switch (((Node) event.getSource()).getId()) {
            case "pvp" -> SceneEnum.WAIT_ROOM.switchTo();
            case "pva", "ava" -> SceneEnum.TIC_TAC_TOE.switchTo();
            default -> {
            }
        }
    }
}
