package domain.player.model;

import domain.game.factories.GameFactory;
import domain.player.table.PlayerTable;
import support.abstracts.AbstractGameBoard;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;
import support.enums.GameStrategyEnum;

import java.sql.Timestamp;

public class Player<T> extends AbstractModel<Player<T>> {

    protected GameFactory game;

    // db fields
    protected String username;
    private GameStrategyEnum gameStrategy;
    private Timestamp lastLogin;

    public Player() {}

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public GameFactory getGame() {
        return game;
    }

    public void setGame(GameFactory game) {
        this.game = game;
    }

    public AbstractGameBoard<?> getGameBoard() {
        return this.game.getGameBoard();
    }

    public GameStrategyEnum getGameStrategy() {
        return gameStrategy;
    }

    public Player<T> setGameStrategy(GameStrategyEnum gameStrategy) {
        this.gameStrategy = gameStrategy;

        return this;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public Player<T> setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;

        return this;
    }

    @Override
    public AbstractTable getTable() {
        return new PlayerTable();
    }
}
