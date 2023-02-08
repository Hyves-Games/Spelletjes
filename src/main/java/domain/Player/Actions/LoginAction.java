package Domain.Player.Actions;

import Domain.Player.Exceptions.LoginFailedException;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractAction;
import Support.Actions.LoginServerAction;
import Support.Exceptions.NoServerConnectionException;
import Support.Helpers.Auth;

public class LoginAction extends AbstractAction {
    private final String username;

    public LoginAction(String username) throws LoginFailedException, NoServerConnectionException {
        this.username = username;

        this.handler();
    }

    @Override
    protected void handler() throws LoginFailedException, NoServerConnectionException {
        new LoginServerAction(this.username);

        Auth.setPlayer(new Player(this.username));
    }
}
