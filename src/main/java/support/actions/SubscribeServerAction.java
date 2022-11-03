package support.actions;

import domain.game.exceptions.GameNotImplementedException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;

public class SubscribeServerAction extends AbstractServerAction {
    public SubscribeServerAction() throws GameNotImplementedException {
        this.handler();
    }

    @Override
    protected void handler() throws GameNotImplementedException {
        try {
            String key = Auth.getPlayer().getLastGameMode().getKey();

            this.command(String.format("subscribe %s", key));
        } catch (NoServerConnectionException ignored) {}
    }
}
