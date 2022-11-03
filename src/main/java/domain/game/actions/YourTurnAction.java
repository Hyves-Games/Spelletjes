package domain.game.actions;

import client.Application;
import support.abstracts.AbstractAction;

public class YourTurnAction extends AbstractAction {
    public YourTurnAction() {
        try {this.handler();} catch (Exception ignored) {}
    }

    @Override
    protected void handler() throws Exception {
        Application.getGameBoard().setPlayerTurn();
    }
}
