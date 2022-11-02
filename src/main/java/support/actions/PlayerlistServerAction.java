package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

public class PlayerlistServerAction extends AbstractServerAction {
    public PlayerlistServerAction() throws NoServerConnectionException {
        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        this.command("get playerlist");
    }
}
