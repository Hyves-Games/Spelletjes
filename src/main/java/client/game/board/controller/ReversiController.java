package client.game.board.controller;

import client.Application;
import domain.game.ai.ReversiAI.Converters.IntArrayToBoolean;
import domain.game.ai.ReversiAI.Converters.IntArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinder;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import support.abstracts.controllers.AbstractGameBoardController;
import support.actions.ChallengeServerAction;
import support.actions.StopGameAction;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

import java.net.URL;
import java.util.Optional;

public class ReversiController extends AbstractGameBoardController {
    @FXML VBox boardContainer;

    public void initialize() {
        HBox row = new HBox();

        Button[] buttons = new Button[64];

        for (int i = 0; i < this.gameBoard.getBoard().length; i++) {
            Button btn = new Button();

            btn.setId("btn_" + (i));
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(this::onMoveClick);
            btn.setPrefHeight(55.0);
            btn.setPrefWidth(55.0);
            btn.setStyle("-fx-background-color: green;");
            btn.setCursor(Cursor.HAND);

            row.getChildren().add(btn);

            if (i % 8 == 7) {
                row.setPrefHeight(60.0);
                row.setPrefWidth(480.0);
                row.setSpacing(5.0);

                boardContainer.getChildren().add(row);
                row = new HBox();
            }

            buttons[i] = btn;
        }

        this.board = buttons;
        this.player_1.setText(this.gameBoard.isStarter() ? "Black " : "White " + this.getPlayerUsername());
        this.player_2.setText(this.gameBoard.isStarter() ? "White " : "Black " + this.getOpponentUsername());

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
        this.changeBoardView();
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
        Integer[] values = this.gameBoard.getBoard();

        long whiteLong = IntArrayToLong.convert(values, 1);
        long blackLong = IntArrayToLong.convert(values, -1);

        int[] availableMoves = MoveFinder.findAvailableMoves(LongToBoolArray.convert(whiteLong), LongToBoolArray.convert(blackLong), this.gameBoard.isStarter());
        boolean[] availableMovesBool = IntArrayToBoolean.convert(availableMoves);
        for (int i = 0; i < values.length; i++) {
            Button btn = this.board[i];

            btn.setDisable(!availableMovesBool[i]);

            if (values[i] != 0) {
                URL black = Application.class.getResource("assets/icons/reversi_black.png");
                URL white = Application.class.getResource("assets/icons/reversi_white.png");

                ImageView black_stone = new ImageView(String.valueOf(white));
                ImageView white_stone = new ImageView(String.valueOf(black));

                black_stone.setFitWidth(35.0);
                white_stone.setFitWidth(35.0);

                black_stone.setFitHeight(35.0);
                white_stone.setFitHeight(35.0);

                if (values[i] == 1) {
                    if(this.gameBoard.isStarter()) {
                        btn.setGraphic(white_stone);
                    } else {
                        btn.setGraphic(black_stone);
                    }
                } else {
                    if(this.gameBoard.isStarter()) {
                        btn.setGraphic(black_stone);
                    } else {
                        btn.setGraphic(white_stone);
                    }
                }
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
            GameModeEnum mode = Auth.getLastGameMode();

            Auth.setLastGameMode(mode);

            mode.create(mode.equals(GameModeEnum.PVA), !mode.equals(GameModeEnum.PVA));
        }
    }
}
