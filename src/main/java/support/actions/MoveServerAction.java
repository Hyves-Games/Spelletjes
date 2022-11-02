package support.actions;

import domain.game.exceptions.MoveNotAllowedException;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;

public class MoveServerAction extends AbstractServerAction {
    private final int index;

    public MoveServerAction(int index) throws MoveNotAllowedException {
        this.index = index;

        this.handler();
    }
    @Override
    protected void handler() throws MoveNotAllowedException {
        this.command("move " + this.index);

        if (!this.isSuccessFull()) {
            throw new MoveNotAllowedException();
        }
    }
}
