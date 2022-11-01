package client.gameModeSelector.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class GameModeSelectorController {

    public void onBackClick() {
        SceneSwitcher.getInstance().change(SceneEnum.GAMESELECTOR);
    }

    public void chooseGamemode(ActionEvent event) {
        switch(((Node)event.getSource()).getId()) {
            case "pvp":
                SceneSwitcher.getInstance().change(SceneEnum.WAITROOM);
                break;
            case "pva":
            case "ava":
                SceneSwitcher.getInstance().change(SceneEnum.TIC_TAC_TOE);
                break;
            default:
                break;
        }
    }
}
