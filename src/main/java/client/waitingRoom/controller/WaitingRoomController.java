package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.enums.SceneEnum;

public class WaitingRoomController {
    public void initialize() {
//        try {
//            new SubscribeServerAction();
//        } catch (GameNotImplementedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void onCancel() {
        new StopGameAction();

        SceneEnum.GAME_MODE_SELECTOR.switchTo();
    }
}
