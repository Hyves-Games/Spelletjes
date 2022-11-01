package client.waitingRoom.controller;

import javafx.event.ActionEvent;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class WaitingRoomController {
    public void onCancel() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.LOBBY);
    }
}
