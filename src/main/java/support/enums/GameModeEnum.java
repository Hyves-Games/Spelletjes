package support.enums;

import domain.game.model.TicTacToe;
import domain.game.model.Game;
import support.abstracts.AbstractGameBoard;

import java.lang.reflect.InvocationTargetException;

public enum GameModeEnum {
    TIC_TAC_TOE(TicTacToe.class);

    private final AbstractGameBoard<?> gameBoard;

    GameModeEnum(Class<? extends AbstractGameBoard<?>> gameBoard) {
        try {
            this.gameBoard = gameBoard.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Game create() {
        return new Game(new TicTacToe());
    }

    public String getKey() {
        return this.gameBoard.getKey();
    }
}
