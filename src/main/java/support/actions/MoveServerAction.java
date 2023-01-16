package support.actions;

import domain.game.exceptions.MoveNotAllowedException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.services.Server;

public class MoveServerAction extends AbstractServerAction {
    private final int index;

    public MoveServerAction(int index) throws MoveNotAllowedException {
        this.index = index;

        this.handler();
    }

    public MoveServerAction(int index, Server connection) throws MoveNotAllowedException {
        this.skip = true;
        this.index = index;
        this.connection = connection;

        this.handler();
    }

    @Override
    protected void handler() throws MoveNotAllowedException {
        try {
            this.command("move " + this.index);
        } catch (NoServerConnectionException ignored) {}

        if (!this.isSuccessFull()) {
            throw new MoveNotAllowedException();
        }
    }
}
