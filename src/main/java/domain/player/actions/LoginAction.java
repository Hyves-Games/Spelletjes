package domain.player.actions;

import domain.player.model.Player;
import support.abstracts.AbstractAction;
import support.actions.LoginServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;

import java.net.Authenticator;

public class LoginAction extends AbstractAction {
    private final String username;

    public LoginAction(String username) throws NoServerConnectionException {
        this.username = username;

        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        new LoginServerAction(this.username);

        Auth.setPlayer(new Player(this.username));
    }
}
