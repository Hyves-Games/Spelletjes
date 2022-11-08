package support.actions;

import domain.player.exceptions.LoginFailedException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.ServerResponse;
import support.services.Server;

public class GetServerPlayerListAction extends AbstractServerAction {

    public GetServerPlayerListAction() throws NoServerConnectionException {
        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        this.command("get playerlist");
    }
}
