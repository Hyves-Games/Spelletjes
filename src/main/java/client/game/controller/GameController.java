package client.game.controller;

import javafx.event.ActionEvent;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class GameController {
    public void onBackClick() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.LOBBY);
    }
}
