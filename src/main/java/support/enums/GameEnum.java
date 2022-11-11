package support.enums;

import domain.game.model.TicTacToe;
import domain.game.model.Game;
import support.abstracts.AbstractGameBoard;

import java.lang.reflect.InvocationTargetException;

public enum GameEnum {
    TIC_TAC_TOE(TicTacToe.class);

    private final Class<? extends AbstractGameBoard<?>> gameBoard;

    GameEnum(Class<? extends AbstractGameBoard<?>> gameBoard) {
        this.gameBoard = gameBoard;
    }

    private AbstractGameBoard<?> getInstance() {
        try {
            return this.gameBoard.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Game create() {
        return new Game(this.getInstance());
    }

    public String getKey() {
        return this.getInstance().getKey();
    }
}
