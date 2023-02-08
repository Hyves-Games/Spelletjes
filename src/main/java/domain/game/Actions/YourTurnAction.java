package Domain.Game.Actions;

import Domain.Player.Model.Player;
import Support.Abstracts.AbstractGameAction;

public class YourTurnAction extends AbstractGameAction {
    public YourTurnAction() {
        this.handler();
    }

    public YourTurnAction(Player<?> player) {
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        this.player.getGameBoard().setPlayerTurn();
    }
}
