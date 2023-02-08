package Support.Actions;

import Support.Abstracts.AbstractServerAction;
import Support.Exceptions.NoServerConnectionException;

public class GetServerPlayerListAction extends AbstractServerAction {

    public GetServerPlayerListAction() throws NoServerConnectionException {
        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        this.command("get playerlist");
    }
}
