package domain.player.actions;

import domain.player.model.Player;
import support.abstracts.AbstractAction;
import support.helpers.Auth;

import java.net.Authenticator;

public class LoginAction extends AbstractAction {
    private final String username;

    public LoginAction(String username) {
        this.username = username;
    }

    @Override
    protected void handler() {
        Auth.setPlayer(new Player(this.username));
    }
}
