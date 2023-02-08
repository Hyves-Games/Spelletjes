package client.game.board.controller;

import client.Application;
import Support.AIs.Reversi.MoveLogic.MoveFinderFast;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import Support.Abstracts.Controllers.AbstractGameBoardController;

import java.net.URL;

public class ReversiController extends AbstractGameBoardController {
    @Override
    protected void createLabels() {
        this.player_1.setText(this.gameBoard.isStarter() ? "Black " : "White " + this.getPlayerUsername());
        this.player_2.setText(this.gameBoard.isStarter() ? "White " : "Black " + this.getOpponentUsername());
    }

    @Override
    protected HBox createRow(HBox row) {
        row.setPrefHeight(60.0);
        row.setPrefWidth(480.0);
        row.setSpacing(5.0);

        return row;
    }

    @Override
    protected Button createBtn(Button btn) {
        btn.setPrefHeight(55.0);
        btn.setPrefWidth(55.0);
        btn.setStyle("-fx-background-color: green;");

        return btn;
    }

    @Override
    protected void changeBoardView() {
        Integer[] values = this.gameBoard.getBoard();
        boolean[] availableMoves = MoveFinderFast.findAvailableMoves(values);

        for (int i = 0; i < values.length; i++) {
            Button btn = this.board[i];

            btn.setDisable(!availableMoves[i]);

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
}
