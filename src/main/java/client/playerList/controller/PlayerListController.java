package client.playerList.controller;

import support.enums.GameModeEnum;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import domain.player.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import support.actions.ChallengeServerAction;
import support.enums.GameEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;

public class PlayerListController {
    @FXML VBox playerContainer;

    private static JsonArray PLAYER_LIST_ITEM;

    public void initialize() {
        playerContainer.setSpacing(10);

        for (JsonElement jsonElement : PLAYER_LIST_ITEM) {
            String player = jsonElement.getAsString();

            if (player.equals(Auth.getPlayer().getUsername())) {
                continue;
            }

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

        if (playerContainer.getChildren().size() == 0) {
            Label noPlayers = new Label("No players found");
            noPlayers.setStyle("-fx-padding: 10px;");
            playerContainer.getChildren().add(noPlayers);
        }
    }

    public void onBackClick() {
        SceneEnum.LOBBY.switchTo();
    }

    public void onInviteClick(ActionEvent event) {
        GameEnum game = GameEnum.TIC_TAC_TOE;
        Player<?> opponent = new Player<>(((Node)event.getSource()).getId());

        Auth.setLastGame(game);
        Auth.setLastGameMode(GameModeEnum.PVP);

        game.create().setAuthPlayer();

        new ChallengeServerAction(opponent, game.getKey());

        SceneEnum.WAIT_ROOM_CHALLENGE.switchTo();
    }

    public static void setPlayerList(JsonArray players) {
        PlayerListController.PLAYER_LIST_ITEM = players;
    }
}
