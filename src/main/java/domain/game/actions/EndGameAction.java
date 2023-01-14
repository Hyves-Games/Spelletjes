package domain.game.actions;

import domain.log.helpers.LogHandler;
import domain.player.model.Player;
import support.abstracts.AbstractGameAction;
import support.abstracts.AbstractGameBoard;
import support.enums.GameEndStateEnum;
import support.enums.GameModeEnum;
import support.enums.ServerResponseEnum;
import support.helpers.Auth;

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
        AbstractGameBoard gameBoard = this.player.getGameBoard();

        GameEndStateEnum endStateEnum = GameEndStateEnum.valueOf(this.type.name());

        gameBoard.setEndState(endStateEnum);
        gameBoard.setGameEnd();
    }
}
