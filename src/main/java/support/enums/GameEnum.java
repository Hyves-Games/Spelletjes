package support.enums;

import domain.game.model.TicTacToe;
import domain.game.model.Game;
import support.abstracts.AbstractGameBoard;

import java.lang.reflect.InvocationTargetException;

public enum GameEnum {
    TIC_TAC_TOE;

    private AbstractGameBoard getInstance() {
        return switch (this) {
            case TIC_TAC_TOE -> new TicTacToe();
        };
    }

    public Game create() {
        return new Game(this.getInstance());
    }

    public String getKey() {
        return this.getInstance().getKey();
    }
}
