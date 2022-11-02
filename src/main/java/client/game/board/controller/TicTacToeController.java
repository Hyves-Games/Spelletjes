package client.game.board.controller;

import client.Application;
import domain.game.model.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import support.abstracts.AbstractGameBoard;
import support.actions.StopGameAction;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.helpers.SceneSwitcher;

import java.util.Optional;
import java.util.*;

public class TicTacToeController {

    @FXML ProgressBar timeLeft;
    @FXML BorderPane container;

    @FXML Label player_1;
    @FXML Label player_2;
    @FXML Label player_1_turn;
    @FXML Label player_2_turn;

    @FXML Button btn_0;
    @FXML Button btn_1;
    @FXML Button btn_2;
    @FXML Button btn_3;
    @FXML Button btn_4;
    @FXML Button btn_5;
    @FXML Button btn_6;
    @FXML Button btn_7;
    @FXML Button btn_8;

    private Button[] board;

    @FXML VBox boardContainer;

    private final AbstractGameBoard gameBoard = Application.getGameBoard();

    public void initialize() {
        this.board = new Button[]{btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8};

        player_1.setText(this.gameBoard.getPlayer().getUsername());
        player_2.setText(this.gameBoard.getOpponent().getUsername());

        this.setTurn();
    }

    public void onMoveClick(ActionEvent event) {
        Integer index = Integer.valueOf(
                ((Node)(event.getSource())).getId().split("_")[1]
        );

        this.changeTurn(!this.gameBoard.setMove(index));
        
        for (int i = 0; i < this.board.length; i++) {
            if(i == index) {
                this.board[i].setText("0");
            }
        }
    }

    public void onLeaveClick(ActionEvent event) {
        Stage stage = (Stage) container.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "Are you sure? you will lose the game.");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            try {
                new StopGameAction();
            } catch (NoServerConnectionException ignored) {}

            SceneSwitcher.getInstance().change(SceneEnum.LOBBY);
        }
    }

    private void setTurn() {
        if (this.gameBoard.isYourTurn()) {
            this.player_1_turn.setText("your turn");
            this.player_2_turn.setText(null);

            this.disableBoard(false);
        } else {
            this.player_1_turn.setText(null);
            this.player_2_turn.setText("your turn");

            this.disableBoard(true);
        }
    }

    private void changeTurn(Boolean condition) {
        this.gameBoard.setYourTurn(condition);

        this.setTurn();
    }

    private void disableBoard(Boolean condition) {
        this.boardContainer.setDisable(condition);
    }
}

