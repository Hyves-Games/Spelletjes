package support.actions;

import com.google.gson.JsonObject;
import domain.game.exceptions.FailedToAcceptChallengeException;
import domain.game.model.Game;
import domain.player.model.AI;
import domain.player.model.Player;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;
import support.services.Server;

public class ChallengeAcceptServerAction extends AbstractServerAction {
    private final JsonObject data;

    public ChallengeAcceptServerAction(JsonObject data) {
        this.data = data;

        this.handler();
    }

    public ChallengeAcceptServerAction(JsonObject data, AI player) {
        this.data = data;
        this.skip = true;
        this.connection = player.getConnection();

        Auth.getLastGame().create().setPlayer(player);

        this.handler();
    }

    @Override
    protected void handler() {
        Integer ChallengeNumber = this.data.get("CHALLENGENUMBER").getAsInt();

        try {
            this.command(String.format("challenge accept %d", ChallengeNumber));
        } catch (NoServerConnectionException ignored) {}
    }
}
