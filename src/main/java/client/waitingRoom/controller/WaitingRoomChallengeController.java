package client.waitingRoom.controller;

import Support.Actions.StopGameAction;
import Support.Enums.SceneEnum;
import Support.Helpers.Auth;

public class WaitingRoomChallengeController {
    public void onCancel() {
        new StopGameAction();

        SceneEnum.LOBBY.switchTo();
    }
}
