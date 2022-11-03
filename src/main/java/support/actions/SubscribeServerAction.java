package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;

public class SubscribeServerAction extends AbstractServerAction {
    public SubscribeServerAction() throws NoServerConnectionException {
        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        switch (Auth.getPlayer().getLastGameMode()) {
            case TIC_TAC_TOE:
                this.command("subscribe tic-tac-toe");
        }
    }
}
