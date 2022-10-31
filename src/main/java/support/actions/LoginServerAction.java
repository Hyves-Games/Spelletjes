package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

public class LoginServerAction extends AbstractServerAction {
    private final String username;

    public LoginServerAction(String username) throws NoServerConnectionException {
        this.username = username;

        this.handler();
    }

    @Override
    protected void handler() throws NoServerConnectionException {
        this.command("login " + this.username);

        System.out.println(this.response.getType());
        System.out.println(this.response.getData());
    }
}
