package client.playerList.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import support.helpers.SceneSwitcher;

import java.lang.reflect.Array;

public class PlayerListController {
    @FXML VBox playerContainer;;

    public void initialize() {
        String[] players = {"player 1", "player 2", "player 3", "player 3", "player 3", "player 3", "player 3", "player 3", "player 3", "player 3", "player 3"};

        playerContainer.setSpacing(10);

        for (String player : players) {
            Button playerButton = new Button();
            playerButton.setText(player + ": Invite to game");
            playerButton.setOnAction(this::onInviteClick);
            playerButton.setId(player);
            playerContainer.getChildren().add(playerButton);
        }
    }

    public void onBackClick(ActionEvent event) {
        SceneSwitcher.switchScene(event, "menu/menu.fxml", "Lobby");
    }

    public void onInviteClick(ActionEvent event) {
        String player = ((Node)event.getSource()).getId();
        System.out.println(player);
    }
}
