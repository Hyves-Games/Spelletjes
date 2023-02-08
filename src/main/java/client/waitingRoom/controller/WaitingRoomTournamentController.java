package client.waitingRoom.controller;

import Domain.Player.Model.AI;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Support.Actions.StopGameAction;
import Support.Enums.GameEnum;
import Support.Enums.SceneEnum;
import Support.Helpers.Auth;
import Support.Services.Server;

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
