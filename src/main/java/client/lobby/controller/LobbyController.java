package client.lobby.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import support.actions.GetServerPlayerListAction;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
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

    public void onPlayerListClick() throws NoServerConnectionException {
        new GetServerPlayerListAction();
    }
}
