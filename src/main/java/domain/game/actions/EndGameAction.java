package domain.game.actions;

import domain.player.model.Player;
import support.abstracts.AbstractGameAction;
import support.abstracts.AbstractGameBoard;
import support.enums.GameEndStateEnum;
import support.enums.ServerResponseEnum;
import support.helpers.Auth;

public class EndGameAction extends AbstractGameAction {
    private final ServerResponseEnum type;

    public EndGameAction(ServerResponseEnum type) {
        this.type = type;

        this.handler();
    }

    public EndGameAction(ServerResponseEnum type, Player player) {
        this.type = type;
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        AbstractGameBoard gameBoard = Auth.getPlayer().getGameBoard();

        gameBoard.setEndState(GameEndStateEnum.valueOf(this.type.toString()));
        gameBoard.setGameEnd();
    }
}
