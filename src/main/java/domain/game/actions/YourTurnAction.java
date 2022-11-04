package domain.game.actions;

import domain.player.model.Player;
import support.abstracts.AbstractGameAction;

public class YourTurnAction extends AbstractGameAction {
    public YourTurnAction() {
        this.handler();
    }

    public YourTurnAction(Player player) {
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        this.player.getGameBoard().setPlayerTurn();
    }
}
