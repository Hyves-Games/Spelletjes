package domain.player.model;

import domain.game.ai.ReversiAI.Interfaces.AI;
import domain.game.model.Game;
import domain.player.query.PlayerQuery;
import domain.player.table.PlayerTable;
import support.abstracts.AbstractGameBoard;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;
import support.enums.GameStrategyEnum;

import java.sql.Timestamp;

public class Player<T> extends AbstractModel<Player<T>> {

    protected Game game;

    protected AI ai;

    // db fields
    protected String username = "username";
    private GameStrategyEnum gameStrategy;
    private Timestamp lastLogin;

    public Player() {
    }

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public Player<T> setUsername(String username) {
        this.username = username;

        return this;
    }

    public AI getAI() {
        return this.ai;
    }

    public Player<?> setAI(AI ai) {
        this.ai = ai;
        this.setUsername(ai.getAIName());
        this.setGameStrategy(ai.getGameStrategy());

        return this;
    }

    public static Player<?> createFromStrategy(GameStrategyEnum strategy) {
        Player<?> player = new Player<>();
        player.setGameStrategy(strategy);
        player.setUsername(strategy.getAI().getAIName());

        return player;
    }

    public Player<?> setAIDepth(int depth) {
        if (this.ai != null) {
            this.ai.setAIDepth(depth);
            this.setUsername(this.ai.getAIName());
        }

        return this;
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

    public GameStrategyEnum getGameStrategy() {
        return gameStrategy;
    }

    public Player<T> setGameStrategy(GameStrategyEnum gameStrategy) {
        this.gameStrategy = gameStrategy;
        this.ai = gameStrategy.getAI();

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
