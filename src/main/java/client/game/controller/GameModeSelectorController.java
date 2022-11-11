package client.game.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import support.enums.GameEnum;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

public class GameModeSelectorController {

    public void onBackClick() {
        SceneEnum.GAME_SELECTOR.switchTo();
    }

    public void onChooseGameMode(ActionEvent event) {
        GameModeEnum mode = GameModeEnum.valueOf(((Node) event.getSource()).getId());

        Auth.setLastGameMode(mode);

        mode.create();
    }
}
