package client.game.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import Support.Enums.GameModeEnum;
import Support.Enums.SceneEnum;
import Support.Helpers.Auth;

public class GameModeSelectorController {

    public void onBackClick() {
        SceneEnum.GAME_SELECTOR.switchTo();
    }

    public void onChooseGameMode(ActionEvent event) {
        GameModeEnum mode = GameModeEnum.valueOf(((Node) event.getSource()).getId());

        Auth.setLastGameMode(mode);

        mode.create(mode.equals(GameModeEnum.PVA), !mode.equals(GameModeEnum.PVA));
    }
}
