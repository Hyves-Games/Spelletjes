package domain.game.model;

import domain.game.exceptions.GameNotImplementedException;
import domain.player.exceptions.FailedToCreateAIException;
import domain.player.model.AI;
import domain.player.model.Player;
import domain.player.query.PlayerQuery;
import javafx.application.Platform;
import support.abstracts.AbstractGameBoard;
import support.actions.ChallengeServerAction;
import support.actions.SubscribeServerAction;
import support.enums.GameEnum;
import support.enums.GameStrategyEnum;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

public class Game {
    private Player<?> player;

    private final AbstractGameBoard<?> gameBoard;

    public Game(AbstractGameBoard<?> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public AbstractGameBoard<?> getGameBoard() {
        return this.gameBoard;
    }

    public void start(Player<?> opponent) {
        this.gameBoard.start(this.player, opponent);

        if (Auth.getPlayer().equals(this.player)) {
            Platform.runLater(() -> {
                SceneSwitcher.getInstance().change(this.gameBoard.getScene());
            });
        }
    }

    public void setPlayer(Player<?> player) {
        player.setGame(this);

        this.player = player;
    }

    public void setAuthPlayer() {
        this.setPlayer(Auth.getPlayer());
    }

    public AI setAIPlayer() {
        Player<?> ai = new PlayerQuery().findOneOrCreate(AI.createUsername());
        if (ai.isNew()) {
            ai.setGameStrategy(GameStrategyEnum.RANDOM);
            ai.save();
        }

        AI aiPlayer = new AI(ai);

        new ChallengeServerAction(aiPlayer, this.gameBoard.getKey());

        return aiPlayer;
    }

    public void searchGame() {
        try {
            new SubscribeServerAction();
        } catch (GameNotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
