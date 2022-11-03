package client.game.board.controller;

import client.Application;
import javafx.application.Platform;
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

    @FXML VBox boardContainer;

    private Button[] board;
    private final AbstractGameBoard gameBoard = Application.getGameBoard();

    public void initialize() {
        this.board = new Button[]{btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8};

        player_1.setText("O " + this.gameBoard.getPlayer().getUsername());
        player_2.setText("X " + this.gameBoard.getOpponent().getUsername());

        this.gameBoard.setEventListenerForTurn(() -> {
            Platform.runLater(this::changeTurn);
        });

        this.gameBoard.setEventListenerForBoard(() -> {
            Platform.runLater(this::changeBoardView);
        });

        this.changeTurn();
    }

    public void onMoveClick(ActionEvent event) {
        int index = Integer.parseInt(((Node) (event.getSource())).getId().split("_")[1]);

        this.gameBoard.doMove(index);
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

    private void changeTurn() {
        if (this.gameBoard.isPlayerTurn()) {
            this.setPlayerTurn();
        } else {
            this.setOpponentTurn();
        }
    }

    private void setPlayerTurn() {
        this.player_1_turn.setText("Your turn");
        this.player_2_turn.setText(null);

        this.boardContainer.setDisable(false);
    }

    private void setOpponentTurn() {
        this.player_1_turn.setText(null);
        this.player_2_turn.setText("Your turn");

        this.boardContainer.setDisable(true);
    }

    private void changeBoardView() {
        Object[] values = this.gameBoard.getBoard();

        for (int i = 0; i < values.length; i++) {
            Integer value = (Integer) values[i];

            if (value != 0) {
                Button btn = this.board[i];

                btn.setDisable(true);
                btn.setText(value == 1 ? "O" : "X");
            }
        }
    }
}

