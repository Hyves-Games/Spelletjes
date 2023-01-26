package client.game.board.controller;

import client.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import support.abstracts.controllers.AbstractGameBoardController;
import support.actions.ChallengeServerAction;
import support.actions.StopGameAction;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

import java.util.Optional;

public class TicTacToeController extends AbstractGameBoardController {
    @FXML Button btn_0;
    @FXML Button btn_1;
    @FXML Button btn_2;
    @FXML Button btn_3;
    @FXML Button btn_4;
    @FXML Button btn_5;
    @FXML Button btn_6;
    @FXML Button btn_7;
    @FXML Button btn_8;

    public void initialize() {
        this.board = new Button[]{btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8};

        this.player_1.setText("O " + this.getPlayerUsername());
        this.player_2.setText("X " + this.getOpponentUsername());

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

    public void onLeaveClick(ActionEvent event) {


        Stage stage = SceneSwitcher.getInstance().getStage();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "Are you sure? you will lose the game.");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            SceneEnum.LOBBY.switchTo();

            new StopGameAction();

            if (Auth.getLastGameMode().equals(GameModeEnum.PVA)) {
                Application.removeAI(this.gameBoard.getOpponent());
            }

        }
    }

    protected void changeTurn() {
        if (this.gameBoard.isPlayerTurn()) {
            this.setPlayerTurn();
        } else {
            this.setOpponentTurn();
        }
    }

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

    protected void showEndScreen() {
        
        if(SceneSwitcher.getInstance().getScene() == SceneEnum.LOBBY) {
            return;
        }

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
       } else {
           GameMode.create(false, false);

           new ChallengeServerAction(this.gameBoard.getOpponent(), Auth.getLastGame().getKey());
       }
    }

}

