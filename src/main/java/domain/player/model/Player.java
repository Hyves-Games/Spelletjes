package domain.player.model;

import domain.game.model.Game;
import support.abstracts.AbstractGameBoard;
import support.enums.GameModeEnum;

public class Player<T> {
    protected final String username;

    protected Game game;
    protected GameModeEnum lastGameMode;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public GameModeEnum getLastGameMode() {
        return this.lastGameMode;
    }

    public void setLastGameMode(GameModeEnum lastGameMode) {
        this.lastGameMode = lastGameMode;
    }

    public AbstractGameBoard getGameBoard() {
        return this.game.getGameBoard();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
