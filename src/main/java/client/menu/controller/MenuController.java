package client.menu.controller;

import domain.player.actions.LoginAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;
import support.services.Server;

public class MenuController
{
    @FXML Label serverStatus;
    @FXML Button loginBtn;
    @FXML TextField loginField;
    @FXML Button playGameBtn;
    @FXML Button playerListBtn;

    public void initialize() {
        String status = Server.getConnection().isConnected() ? "Connected" : "Disconnected";

        this.serverStatus.setText(status);

        if (!Auth.check()) {
            this.setMenuVisibility(false);
        } else {
            this.setLoginVisibility(false);
        }
    }

    public void onLoginClick() {
        try {
            new LoginAction(this.loginField.getText());

            this.setMenuVisibility(true);
            this.setLoginVisibility(false);
        } catch (NoServerConnectionException e) {
            // display error
        }
    }

    public void onPlayGameClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "game/game.fxml", "Tic-Tac-Toe");
    }

    public void onSettingsClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "settings/settings.fxml", "Settings");
    }

    private void setMenuVisibility(Boolean condition)
    {
        this.playGameBtn.setVisible(condition);
        this.playGameBtn.setManaged(condition);

        this.playerListBtn.setVisible(condition);
        this.playerListBtn.setManaged(condition);
    }

    private void setLoginVisibility(Boolean condition)
    {
        this.loginBtn.setVisible(condition);
        this.loginBtn.setManaged(condition);

        this.loginField.setVisible(condition);
        this.loginField.setManaged(condition);
    }
}
