package support.actions;

import domain.player.exceptions.LoginFailedException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.services.Server;

public class LoginServerAction extends AbstractServerAction {
    private final String username;

    public LoginServerAction(String username) throws LoginFailedException, NoServerConnectionException {
        this.username = username;

        this.handler();
    }

    public LoginServerAction(String username, Server connection) throws LoginFailedException, NoServerConnectionException {
        this.username = username;
        this.connection = connection;

        this.handler();
    }

    @Override
    protected void handler() throws LoginFailedException, NoServerConnectionException {
        this.command(String.format("login %s", this.username));

        if (!this.isSuccessFull()) {
            throw new LoginFailedException();
        }
    }
}
