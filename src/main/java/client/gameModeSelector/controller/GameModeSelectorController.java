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
                SceneSwitcher.getInstance().switchByEnum(SceneEnum.WAITROOM);
                break;
            case "pva":
            case "ava":
                SceneSwitcher.getInstance().switchByEnum(SceneEnum.TIC_TAC_TOE);
                break;
            default:
                break;
        }
    }
}
