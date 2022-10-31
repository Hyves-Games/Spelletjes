package support.actions;

import support.interfaces.ActionInterface;
import support.services.Server;

public class MoveServerAction implements ActionInterface {
    private int move;

    public MoveServerAction(int index) {
        this.move = index;
        this.handle();
    }
    @Override
    public void handle() {
        Server server = Server.getInstance();
        server.write("move " + this.move);
    }
}
