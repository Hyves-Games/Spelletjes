package client.waitingRoom.controller;

import domain.player.model.AI;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import support.actions.StopGameAction;
import support.enums.GameEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.services.Server;

public class WaitingRoomTournamentController {
    public VBox winLoss;

    public void initialize() {
        GameEnum.REVERSI.create().setAuthPlayer();

        AI AI = (AI) Auth.getPlayer();

        for (String winLoss : AI.getWinLoss()) {
            HBox row = new HBox();
            Label label = new Label(winLoss);

            label.setStyle("-fx-padding: 5px;");

            row.getChildren().add(label);

            this.winLoss.getChildren().add(row);
        }
    }

    public void onCancel() {
        new StopGameAction();

        Server.getConnection().disconnect();

        SceneEnum.LOGIN.switchTo();
    }
}
