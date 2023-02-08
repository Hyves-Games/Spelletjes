package Domain.Game.Actions;

import Domain.Player.Model.AI;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractGameAction;
import Support.Abstracts.AbstractGameBoard;
import Support.Enums.GameEndStateEnum;
import Support.Enums.ServerResponseEnum;

public class EndGameAction extends AbstractGameAction {
    private final ServerResponseEnum type;

    public EndGameAction(ServerResponseEnum type) {
        this.type = type;

        this.handler();
    }

    public EndGameAction(ServerResponseEnum type, Player<?> player) {
        this.type = type;
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        AbstractGameBoard<?> gameBoard = this.player.getGameBoard();

        gameBoard.setEndState(GameEndStateEnum.valueOf(this.type.toString()));
        gameBoard.setGameEnd();

        if (this.player instanceof AI) {
            String player = gameBoard.getPlayer().getUsername();
            String opponent = gameBoard.getOpponent().getUsername();

            ((AI) this.player).setWinLoss(
                    player + " VS " + opponent + " => " + this.type.toString().toLowerCase()
            );
        }
    }
}
