package domain.game.actions;

import client.Application;
import com.google.gson.JsonObject;
import domain.game.exceptions.GameNotImplementedException;
import domain.game.model.TicTacToe;
import domain.player.model.Player;
import javafx.application.Platform;
import support.abstracts.AbstractAction;
import support.abstracts.AbstractGameBoard;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

public class ViewGameAction extends AbstractAction {
    private SceneEnum scene;
    private final JsonObject data;
    private AbstractGameBoard gameBoard;

    public ViewGameAction(JsonObject data) throws GameNotImplementedException {
        this.data = data;

        this.handler();
    }

    @Override
    protected void handler() throws GameNotImplementedException {
        switch (Auth.getPlayer().getLastGameMode()) {
            case TIC_TAC_TOE -> this.setGame(SceneEnum.TIC_TAC_TOE, new TicTacToe());
            default -> throw new GameNotImplementedException();
        }

        this.gameBoard.setPlayer(Auth.getPlayer())
                .setOpponent(new Player(data.get("OPPONENT").getAsString()));

        Platform.runLater(() -> {
            SceneSwitcher.getInstance().change(this.scene);
        });
    }

    private void setGame(SceneEnum scene, AbstractGameBoard gameBoard) {
        this.scene = scene;
        this.gameBoard = gameBoard;

        Application.setGameBoard(gameBoard);
    }
}
