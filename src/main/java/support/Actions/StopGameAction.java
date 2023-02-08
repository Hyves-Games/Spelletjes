package Support.Actions;

import Support.Abstracts.AbstractServerAction;
import Support.Exceptions.NoServerConnectionException;
import Support.Helpers.Auth;

public class StopGameAction extends AbstractServerAction {
    public StopGameAction() {
        this.handler();
    }

    @Override
    protected void handler() {
        try {
            this.command("forfeit");
        } catch (NoServerConnectionException ignored) {}
    }
}
