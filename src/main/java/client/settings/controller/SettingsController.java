package client.settings.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import support.helpers.Mediaplayer;
import support.helpers.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private Slider volumeSlider;

    @FXML private Label errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        volumeSlider.setValue(Mediaplayer.getVolume() * 100);
    }

    public void onBackClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }

    public void onConnectClick() {

        System.out.println(ip.getText());
        System.out.println(port.getText());
    }
}
