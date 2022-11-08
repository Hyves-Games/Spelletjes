package domain.player.actions;

import client.Application;
import client.playerList.controller.PlayerListController;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.player.exceptions.LoginFailedException;
import domain.player.model.Player;
import javafx.application.Platform;
import support.abstracts.AbstractAction;
import support.actions.LoginServerAction;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;

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
