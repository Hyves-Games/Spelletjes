package Support.Actions;

import Domain.Player.Model.Player;
import Support.Abstracts.AbstractServerAction;
import Support.Exceptions.NoServerConnectionException;
import Support.Services.Server;

public class ChallengeServerAction extends AbstractServerAction {
    private final String key;
    private final String username;

    public ChallengeServerAction(Player<?> player, String key) {
        this.key = key;
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
