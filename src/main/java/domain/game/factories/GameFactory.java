package domain.game.factories;

import domain.game.exceptions.GameNotImplementedException;
import domain.player.exceptions.FailedToCreateAIException;
import domain.player.model.AI;
import domain.player.model.Player;
import javafx.application.Platform;
import support.abstracts.AbstractGameBoard;
import support.actions.ChallengeServerAction;
import support.actions.SubscribeServerAction;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

public class GameFactory {
    private Player<?> player;

    private final AbstractGameBoard<?> gameBoard;

    public GameFactory(AbstractGameBoard<?> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public AbstractGameBoard<?> getGameBoard() {
        return this.gameBoard;
    }

    public void start(Player<?> opponent) {
        this.gameBoard.start(this.player, opponent);

        if (Auth.getPlayer().equals(this.player)) {
            Platform.runLater(() -> SceneSwitcher.getInstance().change(this.gameBoard.getScene()));
        }
    }

    public void setPlayer(Player<?> player) {
        player.setGame(this);

        this.player = player;
    }

    public void setAuthPlayer() {
        this.setPlayer(Auth.getPlayer());
    }

    public void setAIPlayer() {
        try {
            new ChallengeServerAction(new AI(), this.gameBoard.getKey());
        } catch (FailedToCreateAIException | ServerConnectionFailedException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchGame() {
        try {
            new SubscribeServerAction();
        } catch (GameNotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
