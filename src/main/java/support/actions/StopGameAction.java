package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

public class StopGameAction extends AbstractServerAction {
    public StopGameAction() throws NoServerConnectionException {
        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        this.command("forfeit");
    }
}
