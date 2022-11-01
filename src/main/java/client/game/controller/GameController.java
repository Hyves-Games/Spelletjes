package client.game.controller;

import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class GameController {
    public void onBackClick() {
        SceneSwitcher.getInstance().change(SceneEnum.LOBBY);
    }
}
