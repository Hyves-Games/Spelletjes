package support.enums;

import domain.game.model.Reversi;
import domain.game.model.TicTacToe;
import domain.game.factories.GameFactory;
import support.abstracts.AbstractGameBoard;

import java.lang.reflect.InvocationTargetException;

public enum GameEnum {
    TIC_TAC_TOE(TicTacToe.class),
    REVERSI(Reversi.class);

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

    public GameFactory create() {
        return new GameFactory(this.getInstance());
    }

    public String getKey() {
        return this.getInstance().getKey();
    }

    public static GameEnum getByKey(String key) {
        for (GameEnum game : GameEnum.values()) {
            if (game.getKey().equals(key)) {
                return game;
            }
        }

        return null;
    }
}
