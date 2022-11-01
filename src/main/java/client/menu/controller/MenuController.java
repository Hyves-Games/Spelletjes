package client.menu.controller;

import domain.player.actions.LoginAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;
import support.services.Server;

import java.util.Objects;

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

            if(Objects.equals(loginFieldText, "")) {
                showErrorMessage(true);
                return;
            }

            showErrorMessage(false);

            new LoginAction(this.loginField.getText());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Lobby");

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
        this.errorMessage.setManaged(condition);
        this.errorMessage.setVisible(condition);
    }
}
