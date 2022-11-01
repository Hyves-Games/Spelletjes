package client.settings.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import support.helpers.AudioPlayer;
import support.actions.ConnectServerAction;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.SceneSwitcher;

public class SettingsController {

    @FXML TextField ip;
    @FXML TextField port;
    @FXML Label errorMessage;
    @FXML Label volumeLevel;
    @FXML Slider volumeSlider;

    public void initialize() {
        this.port.setText("7789");
        this.ip.setText("localhost");

        this.errorMessage.setManaged(false);

        this.volumeSlider.setValue(AudioPlayer.getVolume() * 100);
        this.volumeLevel.setText(Long.toString(Math.round(volumeSlider.getValue())));

        this.volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                AudioPlayer.setVolume(volumeSlider.getValue() / 100);
                volumeLevel.setText(Long.toString(Math.round(volumeSlider.getValue())));
            }
        });
    }

    public void onBackClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }

    public void onConnectClick() {
        Integer port = Integer.parseInt(this.port.getText());

        try {
            new ConnectServerAction(this.ip.getText(), port);

            this.errorMessage.setText("");
            this.errorMessage.setManaged(false);
        } catch (ServerConnectionFailedException e) {
            this.errorMessage.setManaged(true);
            this.errorMessage.setText("Connection failed to server");
        }
    }
}
