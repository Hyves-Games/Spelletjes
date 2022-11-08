package support.abstracts.controllers;

import domain.player.model.AI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import support.abstracts.AbstractGameBoard;
import support.helpers.Auth;

public abstract class AbstractGameBoardController {
    @FXML public Label player_1;
    @FXML public Label player_2;
    @FXML public Label player_1_turn;
    @FXML public Label player_2_turn;

    @FXML public ProgressBar timeLeft;
    @FXML public VBox boardContainer;

    protected Button[] board;
    protected final AbstractGameBoard gameBoard = Auth.getPlayer().getGameBoard();

    protected Boolean isPlayerAI() {
        return Auth.getPlayer() instanceof AI;
    }

    protected void setPlayerTurn() {
        this.player_1_turn.setText("Your turn");
        this.player_2_turn.setText(null);

        this.boardContainer.setDisable(this.isPlayerAI());
    }

    protected void setOpponentTurn() {
        this.player_1_turn.setText(null);
        this.player_2_turn.setText("Your turn");

        this.boardContainer.setDisable(true);
    }

    protected abstract void changeTurn();
    protected abstract void changeBoardView();
}