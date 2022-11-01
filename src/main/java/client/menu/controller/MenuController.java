package client.menu.controller;

import domain.game.model.TicTacToe;
import domain.player.actions.LoginAction;
import domain.player.exceptions.LoginFailedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;
import support.services.Server;

public class MenuController
{
    @FXML Label serverStatus;
    @FXML Label menuTitle;
    @FXML Label errorMessage;
    @FXML Button loginBtn;
    @FXML TextField loginField;
    @FXML Button playGameBtn;
    @FXML Button playerListBtn;

    public void initialize() {
        String status = Server.getConnection().isConnected() ? "Connected" : "Disconnected";

        this.serverStatus.setText(status);
        this.loginField.setFocusTraversable(false);

        if (!Auth.check()) {
            this.setMenuVisibility(false);
        } else {
            this.setLoginVisibility(false);
        }
    }

    public void onLoginClick(ActionEvent event) {
        try {
            String loginFieldText = this.loginField.getText();
            System.out.println(loginFieldText);
            if (loginFieldText == null || loginFieldText.trim().isEmpty()) {
                this.showErrorMessage(true);
                this.errorMessage.setText("Enter a username");

                return;
            }

            this.showErrorMessage(false);
            this.errorMessage.setText("");

            new LoginAction(this.loginField.getText());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Lobby");

            this.setMenuVisibility(true);
            this.setLoginVisibility(false);

        } catch (LoginFailedException e) {
            this.errorMessage.setText("Failed to login");
        } catch (NoServerConnectionException e) {
            this.errorMessage.setText("No connection");
        }
    }

    public void onPlayGameClick() {
        SceneSwitcher.getInstance().change(SceneEnum.GAMESELECTOR);
    }

    public void onSettingsClick() {
        SceneSwitcher.getInstance().change(SceneEnum.SETTING);
    }

    public void onPlayerListClick(ActionEvent event) {
        SceneSwitcher.getInstance().change(SceneEnum.PLAYERLIST);
    }

    private void setMenuVisibility(Boolean condition)
    {
        this.playGameBtn.setVisible(condition);
        this.playGameBtn.setManaged(condition);

        this.playerListBtn.setVisible(condition);
        this.playerListBtn.setManaged(condition);

        if(condition) {
            this.menuTitle.setText("Menu");
        } else {
            this.menuTitle.setText("Login");
        }
    }

    private void setLoginVisibility(Boolean condition)
    {
        this.loginBtn.setVisible(condition);
        this.loginBtn.setManaged(condition);

        this.loginField.setVisible(condition);
        this.loginField.setManaged(condition);
    }

    private void showErrorMessage(Boolean condition)
    {
        this.errorMessage.setVisible(condition);
        this.errorMessage.setManaged(condition);
    }
}
