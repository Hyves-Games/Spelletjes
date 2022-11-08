package client.game.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

public class GameModeSelectorController {

    public void onBackClick() {
        SceneEnum.GAME_SELECTOR.switchTo();
    }

    public void onChooseGameMode(ActionEvent event) {
        GameModeEnum game = Auth.getPlayer().getLastGameMode();

        switch (((Node) event.getSource()).getId()) {
            case "pvp" -> {
                game.create().setAuthPlayer();

                SceneSwitcher.getInstance().change(SceneEnum.WAIT_ROOM);
            }
            case "pva" -> {
                game.create().setAuthPlayer();
                game.create().setAIPlayer();
            }
        }
    }
}
