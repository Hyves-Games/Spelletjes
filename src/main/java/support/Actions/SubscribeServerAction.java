package Support.Actions;

import Domain.Game.Exceptions.GameNotImplementedException;
import Support.Abstracts.AbstractServerAction;
import Support.Exceptions.NoServerConnectionException;
import Support.Helpers.Auth;

public class SubscribeServerAction extends AbstractServerAction {
    public SubscribeServerAction() throws GameNotImplementedException {
        this.handler();
    }

    @Override
    protected void handler() throws GameNotImplementedException {
        try {
            String key = Auth.getLastGame().getKey();

            this.command(String.format("subscribe %s", key));
        } catch (NoServerConnectionException ignored) {}
    }
}
