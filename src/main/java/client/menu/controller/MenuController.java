package client.menu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import support.helpers.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable
{
    public Boolean authenticated = false;
    @FXML Label serverStatus;
    @FXML Button loginBtn;
    @FXML TextField loginField;
    @FXML Button playGameBtn;
    @FXML Button playerListBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!authenticated) {
            playGameBtn.setVisible(false);
            playerListBtn.setVisible(false);
        }
    }

    public void onLoginClick(ActionEvent event) {
        playGameBtn.setVisible(true);
        playerListBtn.setVisible(true);
        loginField.setVisible(false);
        loginBtn.setVisible(false);
    }

    public void onPlayGameClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "waitingRoom/waitingroom.fxml", "Waiting room");
    }

    public void onSettingsClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "settings/settings.fxml", "Settings");
    }

}
