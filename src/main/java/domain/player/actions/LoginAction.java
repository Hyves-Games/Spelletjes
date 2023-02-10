package domain.player.actions;

import domain.player.exceptions.LoginFailedException;
import domain.player.model.Player;
import domain.player.query.PlayerQuery;
import support.abstracts.AbstractAction;
import support.actions.LoginServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;

public class LoginAction extends AbstractAction {
    private final String username;

    public LoginAction(String username) throws LoginFailedException, NoServerConnectionException {
        this.username = username;

        this.handler();
    }

    @Override
    protected void handler() throws LoginFailedException, NoServerConnectionException {
        new LoginServerAction(this.username);

        Player<?> player = new PlayerQuery().findOneOrCreate(this.username);

        Auth.setPlayer(player);
    }
}
