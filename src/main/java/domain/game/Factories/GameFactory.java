package Domain.Game.Factories;

import Domain.Game.Exceptions.GameNotImplementedException;
import Domain.Player.Exceptions.FailedToCreateAIException;
import Domain.Player.Model.AI;
import Domain.Player.Model.Player;
import javafx.application.Platform;
import Support.Abstracts.AbstractGameBoard;
import Support.Actions.ChallengeServerAction;
import Support.Actions.SubscribeServerAction;
import Support.Exceptions.ServerConnectionFailedException;
import Support.Helpers.Auth;
import Support.Helpers.SceneSwitcher;

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
