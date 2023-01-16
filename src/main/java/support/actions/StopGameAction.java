package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

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
