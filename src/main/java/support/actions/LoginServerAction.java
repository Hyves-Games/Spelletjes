package support.actions;

import domain.player.exceptions.LoginFailedException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

public class LoginServerAction extends AbstractServerAction {
    private final String username;

    public LoginServerAction(String username) throws LoginFailedException, NoServerConnectionException {
        this.username = username;

        this.handler();
    }

    @Override
    protected void handler() throws LoginFailedException, NoServerConnectionException {
        this.command("login " + this.username);

        if (!this.isSuccessFull()) {
            throw new LoginFailedException();
        }
    }
}
