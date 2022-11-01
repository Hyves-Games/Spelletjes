package client.playerList.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

import java.lang.reflect.Array;

public class PlayerListController {
    @FXML VBox playerContainer;

    public void initialize() {
        //Client & server deze array moet een lijst worden met de geconnecte players.
        String[] players = {"player 1", "player 2", "player 3", "player 4", "player 5", "player 6", "player 7", "player 8", "player 9", "player 10", "player 11"};

        playerContainer.setSpacing(10);

        for (String player : players) {
            HBox playerRow = new HBox();
            Label playerName = new Label(player);
            Button playerInviteButton = new Button("Invite");

            playerInviteButton.setOnAction(this::onInviteClick);
            playerInviteButton.setId(player);

            playerRow.setAlignment(Pos.CENTER_LEFT);
            playerName.setStyle("-fx-padding: 10px;");

            playerContainer.getChildren().add(playerRow);
            playerRow.getChildren().add(playerName);
            playerRow.getChildren().add(playerInviteButton);
        }
    }

    public void onBackClick() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.LOBBY);
    }

    public void onInviteClick(ActionEvent event) {
        // Hier kan de client & server verder gaan met het inviten van een player in de player variabele hieronder zit de naam van de player.
        String player = ((Node)event.getSource()).getId();
    }
}
