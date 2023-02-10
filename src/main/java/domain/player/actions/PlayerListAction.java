package domain.player.actions;

import client.playerList.controller.PlayerListController;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import support.abstracts.AbstractAction;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;

public class PlayerListAction extends AbstractAction {
    private final JsonObject data;

    public PlayerListAction(JsonObject data) throws NoServerConnectionException {
        this.data = data;

        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        PlayerListController.setPlayerList(data.get("players").getAsJsonArray());

        Platform.runLater(SceneEnum.PLAYER_LIST::switchTo);
    }
}
