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
        SceneSwitcher.getInstance().change(SceneEnum.GAME_SELECTOR);
    }

    public void onSettingsClick() {
        SceneSwitcher.getInstance().change(SceneEnum.SETTING);
    }

    public void onPlayerListClick() {
        SceneSwitcher.getInstance().change(SceneEnum.PLAYER_LIST);
    }
}
