package client.gameModeSelector.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class GameModeSelectorController {
    public void onBackClick() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.GAMESELECTOR);
    }

    public void chooseGamemode(ActionEvent event) {
        switch(((Node)event.getSource()).getId()) {
            case "pvp":
                break;
            case "pva":
                break;
            case "ava":
                break;
            default:
                break;
        }
    }
}
