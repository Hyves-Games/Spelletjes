package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.enums.GameEnum;
import support.enums.SceneEnum;

public class WaitingRoomChallengeController {
    public void initialize() {
        GameEnum.TIC_TAC_TOE.create().setAuthPlayer();
    }

    public void onCancel() {
        new StopGameAction();

        SceneEnum.LOBBY.switchTo();
    }
}
