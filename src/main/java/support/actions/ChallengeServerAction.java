package support.actions;

import domain.player.model.Player;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.services.Server;

public class ChallengeServerAction extends AbstractServerAction {
    private final String key;
    private final String username;

    public ChallengeServerAction(Player<?> player, String key) {
        this.key = key;
        this.username = player.getUsername();

        this.handler();
    }

    public ChallengeServerAction(Player<?> player, String key, Server connection) {
        this.key = key;
        this.connection = connection;
        this.username = player.getUsername();

        this.handler();
    }

    @Override
    protected void handler() {
        try {
            this.command(String.format("challenge %s %s", this.username, this.key));
        } catch (NoServerConnectionException ignored) {}
    }
}
