package support.actions;

import com.google.gson.JsonObject;
import domain.game.exceptions.FailedToAcceptChallengeException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.services.Server;

public class ChallengeAcceptServerAction extends AbstractServerAction {
    private final JsonObject data;

    public ChallengeAcceptServerAction(JsonObject data) {
        this.data = data;

        this.handler();
    }

    public ChallengeAcceptServerAction(JsonObject data, Server connection) {
        this.data = data;
        this.skip = true;
        this.connection = connection;

        this.handler();

        connection.responseHandled();
    }

    @Override
    protected void handler() {
        Integer ChallengeNumber = this.data.get("CHALLENGENUMBER").getAsInt();

        try {
            this.command(String.format("challenge accept %d", ChallengeNumber));
        } catch (NoServerConnectionException ignored) {}
    }
}
