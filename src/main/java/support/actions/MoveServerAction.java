package support.actions;

import support.abstracts.AbstractServerAction;

public class MoveServerAction extends AbstractServerAction {
    private final int index;

    public MoveServerAction(int index) {
        this.index = index;

        this.handler();
    }
    @Override
    protected void handler() {
        this.server.write("move " + this.index);
    }
}
