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

    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private Label errorMessage;
    @FXML private Label volumeLevel;
    @FXML private Slider volumeSlider;

    public void initialize() {
        this.port.setText("7789");
        this.ip.setText("localhost");

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
        } catch (ServerConnectionFailedException e) {
            this.errorMessage.setText("Connection failed to server");
        }
    }
}
