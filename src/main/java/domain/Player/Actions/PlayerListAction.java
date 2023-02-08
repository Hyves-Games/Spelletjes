package Domain.Player.Actions;

import client.playerList.controller.PlayerListController;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import Support.Abstracts.AbstractAction;
import Support.Enums.SceneEnum;
import Support.Exceptions.NoServerConnectionException;

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
