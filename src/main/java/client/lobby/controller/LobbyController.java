package client.lobby.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;
import support.services.Server;

public class LobbyController
{
    @FXML Label serverStatus;

    public void initialize() {
        String status = Server.getConnection().isConnected() ? "Connected" : "Disconnected";

        this.serverStatus.setText(status);
    }

    public void onPlayGameClick() {
        SceneEnum.GAME_SELECTOR.switchTo();
    }

    public void onSettingsClick() {
        SceneEnum.SETTING.switchTo();
    }

    public void onPlayerListClick() {
        SceneEnum.PLAYER_LIST.switchTo();
    }
}
