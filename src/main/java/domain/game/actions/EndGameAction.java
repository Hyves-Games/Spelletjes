package domain.game.actions;

import client.Application;
import support.abstracts.AbstractAction;
import support.abstracts.AbstractGameBoard;
import support.enums.GameEndStateEnum;
import support.enums.ServerResponseEnum;

public class EndGameAction extends AbstractAction {
    private final ServerResponseEnum type;

    public EndGameAction(ServerResponseEnum type) {
        this.type = type;

        try {this.handler();} catch (Exception ignored) {}
    }

    @Override
    protected void handler() throws Exception {
        AbstractGameBoard gameBoard = Application.getGameBoard();

        gameBoard.setEndState(GameEndStateEnum.valueOf(this.type.toString()));
        gameBoard.setGameEnd();
    }
}
