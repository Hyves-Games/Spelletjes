package client.authenticator.controller;

import domain.player.actions.LoginAction;
import domain.player.exceptions.FailedToCreateAIException;
import domain.player.exceptions.LoginFailedException;
import domain.player.model.AI;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import support.abstracts.AbstractController;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;
import support.services.Server;

public class LoginController extends AbstractController {
    public Label errorMessage;
    public Label serverStatus;
    public TextField loginField;

    public void initialize() {
        String status = Server.getConnection().isConnected() ? "Connected" : "Disconnected";

        this.serverStatus.setText(status);
        this.errorMessage.setManaged(false);
    }

    public void onLoginClick() {
        try {
            String loginFieldText = this.loginField.getText();

            if (loginFieldText == null || loginFieldText.trim().isEmpty()) {
                this.errorMessage.setText("Enter a username");

                return;
            }

            this.setError(this.errorMessage, null);

            new LoginAction(loginFieldText.trim());

            SceneEnum.LOBBY.switchTo();
        } catch (LoginFailedException e) {
            this.setError(this.errorMessage, "Failed to login");
        } catch (NoServerConnectionException e) {
            this.setError(this.errorMessage, "No connection");
        }
    }

    public void onSettingsClick() {
        SceneEnum.SETTING.switchTo();
    }

    public void onTournamentClick() {
        try {
            Auth.setPlayer(new AI());

            SceneEnum.TOURNAMENT_ROOM.switchTo();
        } catch (ServerConnectionFailedException | FailedToCreateAIException e) {
            throw new RuntimeException(e);
        }
    }
}
