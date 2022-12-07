package client.game.board.controller;

import client.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import support.abstracts.controllers.AbstractGameBoardController;
import support.actions.ChallengeServerAction;
import support.actions.StopGameAction;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

import java.util.ArrayList;
import java.util.Optional;

public class ReversiController extends AbstractGameBoardController {
    @FXML GridPane board_grid;

    public void initialize() {
        this.player_1.setText("O " + this.getPlayerUsername());
        this.player_2.setText("X " + this.getOpponentUsername());
        int col = 0;
        int row = 0;

        Button[] buttons = new Button[64];
        for (int i = 0; i < this.gameBoard.getBoard().length; i++) {
            Button btn = new Button();
            btn.setId("btn_" + (i+1));
            if (this.gameBoard.getBoard()[i] == 1) {
                // Player's moves
                btn.setText("X");
            } else if (this.gameBoard.getBoard()[i] == -1) {
                // Opponents moves
                btn.setText("O");
            } else {
                // Open spots
                btn.setText("[]");
            }
            btn.setPrefHeight(60.0);
            btn.setPrefWidth(60.0);
            btn.setPadding(new Insets(10));
            btn.setOnAction(this::onMoveClick);
            if (i != 0 && i % 8 == 0) {
                row++;
                col = 0;
            }
            this.board_grid.add(btn, col, row);
            col++;
            buttons[i] = btn;
        }
        this.board = buttons;

        this.gameBoard.addEventListenerForTurn(() -> {
            Platform.runLater(this::changeTurn);
        });

        this.gameBoard.addEventListenerForBoard(() -> {
            Platform.runLater(this::changeBoardView);
        });
        this.gameBoard.addEventListenerForEnd(() -> {
            if (this.isPlayerAI()) {
                Platform.runLater(SceneEnum.WAIT_ROOM_TOURNAMENT::switchTo);
            } else {
                Platform.runLater(this::showEndScreen);
            }
        });
        this.changeTurn();
    }

    public void onMoveClick(ActionEvent event) {
        int index = Integer.parseInt(((Node) (event.getSource())).getId().split("_")[1]);

        this.gameBoard.doMove(index);
    }
    @Override
    protected void changeTurn() {
        if (this.gameBoard.isPlayerTurn()) {
            this.setPlayerTurn();
        } else {
            this.setOpponentTurn();
        }
    }

    @Override
    protected void changeBoardView() {
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

    public void onLeaveClick(ActionEvent event) {
        Stage stage = SceneSwitcher.getInstance().getStage();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "Are you sure? you will lose the game.");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            new StopGameAction();

            if (Auth.getLastGameMode().equals(GameModeEnum.PVA)) {
                Application.removeAI(this.gameBoard.getOpponent());
            }

            SceneEnum.LOBBY.switchTo();
        }
    }
    protected void showEndScreen() {
        GameModeEnum GameMode = Auth.getLastGameMode();
        Stage stage = SceneSwitcher.getInstance().getStage();

        ButtonBar.ButtonData done = ButtonBar.ButtonData.OK_DONE;

        ButtonType lobbyButton = new ButtonType("To the lobby", done);
        ButtonType rematchButton = new ButtonType("Rematch!");

        String message = switch (this.gameBoard.getEndState()) {
            case WIN -> "You have won the game!";
            case LOSS -> "You have lost the game from " + this.getOpponentUsername() + ".";
            case DRAW -> "It is a draw!";
        };

        Alert alert = new Alert(Alert.AlertType.NONE, message, lobbyButton, rematchButton);

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get().getButtonData() == done) {
            if (GameMode.equals(GameModeEnum.PVA)) {
                Application.removeAI(this.gameBoard.getOpponent());
            }

            SceneEnum.LOBBY.switchTo();
        } else {
            GameMode.create(false, false);

            new ChallengeServerAction(this.gameBoard.getOpponent(), Auth.getLastGame().getKey());
        }
    }
}
