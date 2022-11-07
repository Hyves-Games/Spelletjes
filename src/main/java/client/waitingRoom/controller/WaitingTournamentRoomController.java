package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.services.Server;

public class WaitingTournamentRoomController {
    public void initialize() {
        GameModeEnum.TIC_TAC_TOE.create().setAuthPlayer();
    }

    public void onCancel() {
        new StopGameAction();

        Server.getConnection().disconnect();

        SceneEnum.LOGIN.switchTo();
    }
}
