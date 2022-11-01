package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

public class PlaylistServerAction extends AbstractServerAction {
    public PlaylistServerAction() throws NoServerConnectionException {
        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        this.command("get playlist");
    }
}
