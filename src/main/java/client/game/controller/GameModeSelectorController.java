package client.game.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class GameModeSelectorController {

    public void onBackClick() {
        SceneSwitcher.getInstance().change(SceneEnum.GAME_SELECTOR);
    }

    public void onChooseGameMode(ActionEvent event) {
        switch (((Node) event.getSource()).getId()) {
            case "pvp" -> SceneSwitcher.getInstance().change(SceneEnum.WAIT_ROOM);
            case "pva", "ava" -> SceneSwitcher.getInstance().change(SceneEnum.TIC_TAC_TOE);
            default -> {
            }
        }
    }
}
