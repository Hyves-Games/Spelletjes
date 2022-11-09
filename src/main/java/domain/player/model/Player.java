package domain.player.model;

import domain.game.model.Game;
import support.abstracts.AbstractGameBoard;
import support.enums.GameEnum;

public class Player<T> {
    protected Game game;
    protected final String username;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public AbstractGameBoard<?> getGameBoard() {
        return this.game.getGameBoard();
    }
}
