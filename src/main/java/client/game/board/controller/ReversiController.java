package client.game.board.controller;

import client.Application;
import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;
import domain.game.ai.ReversiAI.Converters.IntArrayToBoolean;
import domain.game.ai.ReversiAI.Converters.IntArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
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
import support.enums.ServerResponseEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ReversiController extends AbstractGameBoardController {
    @FXML GridPane board_grid;

    private boolean[] whitePieces;
    private boolean[] blackPieces;

    public void initialize() {
        boolean playerIsStarter = this.gameBoard.getStarter().getUsername().equals(this.gameBoard.getPlayer().getUsername());
        /*
        this.gameBoard.setMove(27, playerIsStarter ? -1 : 1, true);
        this.gameBoard.setMove(28, playerIsStarter ? 1 : -1, true);
        this.gameBoard.setMove(35, playerIsStarter ? 1 : -1, true);
        this.gameBoard.setMove(36, playerIsStarter ? -1 : 1, true);
         */
        this.gameBoard.setMove(27, -1, true);
        this.gameBoard.setMove(28, 1, true);
        this.gameBoard.setMove(35, 1, true);
        this.gameBoard.setMove(36, -1, true);

        this.player_1.setText(playerIsStarter ? "X " + this.getPlayerUsername() : "O " + this.getPlayerUsername());
        this.player_2.setText(playerIsStarter ? "O " + this.getOpponentUsername() : "X " + this.getOpponentUsername());
        int col = 0;
        int row = 0;

        Button[] buttons = new Button[64];
        for (int i = 0; i < this.gameBoard.getBoard().length; i++) {
            Button btn = new Button();
            btn.setId("btn_" + (i));
            if (this.gameBoard.getBoard()[i] == 1) {
                // Player's moves
                btn.setText(playerIsStarter ? "X" : "O");
            } else if (this.gameBoard.getBoard()[i] == -1) {
                // Opponents moves
                btn.setText(playerIsStarter ? "O" : "X");
            } else {
                // Open spots
                btn.setText("");
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

    private void runLogic() {
        boolean playerIsStarter = this.gameBoard.getStarter().getUsername().equals(this.gameBoard.isPlayerTurn() ? this.gameBoard.getPlayer().getUsername() : this.gameBoard.getOpponent().getUsername());
        boolean[] playerPieces = IntArrayToBoolean.convert(this.gameBoard.getBoard(), 1);
        boolean[] opponentPieces = IntArrayToBoolean.convert(this.gameBoard.getBoard(), -1);
        System.out.println("LOGIC CALLED, isStarter: " + playerIsStarter + ", isTurn: " + this.gameBoard.isPlayerTurn() + ", index: " + this.gameBoard.getLastMove());
        int index = this.gameBoard.getLastMove();
        //boolean isWhite = this.gameBoard.isPlayerTurn() ? playerIsStarter ? false : true : playerIsStarter ?
        //boolean isWhite = this.gameBoard.isPlayerTurn() != playerIsStarter;
        boolean isWhite = this.gameBoard.isPlayerTurn();
//        if (playerIsStarter && this.gameBoard.isPlayerTurn()) {
//            isWhiteTurn = false;
//        }
//        if (!playerIsStarter && !this.gameBoard.isPlayerTurn()) {
//            isWhiteTurn = false;
//        }
        System.out.println("Last move was:");
        System.out.println(index);
        this.whitePieces = playerIsStarter ? opponentPieces : playerPieces;
        this.blackPieces = playerIsStarter ? playerPieces : opponentPieces;
        MakeMove.makeMove(
                this.whitePieces,
                this.blackPieces,
                isWhite,
                index);
//        System.out.println(pieces.playerWhitePieces);
//        this.whitePieces = LongToBoolArray.convert(pieces.playerWhitePieces);
//        this.blackPieces = LongToBoolArray.convert(pieces.playerBlackPieces);
        System.out.println("White pieces");
        for (int i = 0; i < playerPieces.length; i++) {
            if (playerPieces[i]) {
                System.out.println(i);
            }
        }
        System.out.println("Black pieces");
        for (int i = 0; i < this.blackPieces.length; i++) {
            if (this.blackPieces[i]) {
                System.out.println(i);
            }
        }
        for (int i = 0; i < 64; i++) {
            if (this.whitePieces[i]) {
                this.gameBoard.setMove(i, isWhite ? 1 : -1, true);
            }
            else if (this.blackPieces[i]) {
                this.gameBoard.setMove(i, isWhite ? -1 : 1, true);
            }
        }
        BoardPrinter.printBoard(BoolArrayToLong.convert(this.whitePieces), BoolArrayToLong.convert(this.blackPieces)); //MakeMoveFast.boolArrayToLong(this.whitePieces),MakeMoveFast.boolArrayToLong(this.blackPieces));
    }
    @Override
    protected void changeTurn() {
        if (this.gameBoard.isPlayerTurn()) {
            this.setPlayerTurn();
        } else {
            this.setOpponentTurn();
        }
        this.runLogic();
    }

    @Override
    protected void changeBoardView() {
        Object[] values = this.gameBoard.getBoard();
        boolean playerIsStarter = this.gameBoard.getStarter().getUsername().equals(this.gameBoard.getPlayer().getUsername());
        for (int i = 0; i < values.length; i++) {
            Integer value = (Integer) values[i];

            if (value != 0) {
                Button btn = this.board[i];

                btn.setDisable(true);
                btn.setText(value == 1 ? playerIsStarter ? "X" : "O" : playerIsStarter ? "O" : "X");
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
