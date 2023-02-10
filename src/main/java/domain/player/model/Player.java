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

    protected String username;
    protected Timestamp lastLogin;
    protected GameStrategyEnum gameStrategy;

    public Player() {}

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public AbstractTable getTable() {
        return new PlayerTable();
    }
}
