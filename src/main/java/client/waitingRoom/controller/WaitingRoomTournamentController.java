package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.enums.GameEnum;
import support.enums.SceneEnum;
import support.services.Server;

public class WaitingRoomTournamentController {
    public void initialize() {
        GameEnum.REVERSI.create().setAuthPlayer();
    }

    public void onCancel() {
        new StopGameAction();

        Server.getConnection().disconnect();

        SceneEnum.LOGIN.switchTo();
    }
}
