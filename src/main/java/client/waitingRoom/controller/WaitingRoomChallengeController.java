package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.enums.SceneEnum;

public class WaitingRoomChallengeController {
    public void onCancel() {
        new StopGameAction();

        SceneEnum.LOBBY.switchTo();
    }
}
