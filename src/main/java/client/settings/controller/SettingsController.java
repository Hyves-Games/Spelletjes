package client.settings.controller;

import domain.setting.enums.Settings;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import support.abstracts.AbstractController;
import support.enums.SceneEnum;
import support.helpers.AudioPlayer;
import support.actions.ConnectServerAction;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

public class SettingsController extends AbstractController {

    @FXML TextField ip;
    @FXML TextField port;
    @FXML Label errorMessage;
    @FXML Label volumeLevel;
    @FXML Slider volumeSlider;

    public void initialize() {
        this.ip.setText(Settings.SERVER_IP_ADDRESS.getStringValue());
        this.port.setText(Settings.SERVER_PORT.getStringValue());

        this.errorMessage.setManaged(false);

        this.volumeSlider.setValue(AudioPlayer.getVolume() * 100);
        this.volumeLevel.setText(Long.toString(Math.round(volumeSlider.getValue())));

        this.volumeSlider.valueProperty().addListener(observable -> {
            AudioPlayer.setVolume(volumeSlider.getValue() / 100);
            volumeLevel.setText(Long.toString(Math.round(volumeSlider.getValue())));
        });
    }

    public void onBackClick() {
        Settings.SERVER_IP_ADDRESS.saveWithValue(ip.getText());
        Settings.SERVER_PORT.saveWithValue(port.getText());
        Settings.MUSIC_VOLUME_LOBBY.saveWithValue(volumeSlider.getValue() / 100);

        if (Auth.check()) {
            SceneEnum.LOBBY.switchTo();
        } else {
            SceneEnum.LOGIN.switchTo();
        }
    }

    public void onConnectClick() {
        try {
            Integer port = Integer.parseInt(this.port.getText());

            new ConnectServerAction(this.ip.getText(), port);

            this.setError(this.errorMessage, null);
        } catch (ServerConnectionFailedException e) {
            this.setError(this.errorMessage,"Connection failed to server");
        } catch (NumberFormatException e) {
            this.setError(this.errorMessage,"Invalid port number");
        }
    }
}
