package client.waitingRoom.controller;

import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class WaitingRoomController {
    public void onCancel() {
        SceneSwitcher.getInstance().change(SceneEnum.GAMEMODESELECTOR);
    }
}
