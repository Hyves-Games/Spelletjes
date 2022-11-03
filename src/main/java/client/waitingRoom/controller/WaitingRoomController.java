package client.waitingRoom.controller;

import support.actions.StopGameAction;
import support.actions.SubscribeServerAction;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.helpers.SceneSwitcher;

public class WaitingRoomController {
    public void initialize() {
        try {
            new SubscribeServerAction();
        } catch (NoServerConnectionException ignored) {}
    }

    public void onCancel() {
        try {
            new StopGameAction();
        } catch (NoServerConnectionException ignored) {}

        SceneEnum.GAME_MODE_SELECTOR.switchTo();
    }
}
