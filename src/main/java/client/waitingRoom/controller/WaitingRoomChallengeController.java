package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.enums.GameEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;

public class WaitingRoomChallengeController {
    public void initialize() {
        Auth.getLastGame().create().setAuthPlayer();
    }

    public void onCancel() {
        new StopGameAction();

        SceneEnum.LOBBY.switchTo();
    }
}
