package client.game.board.controller;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import Support.Abstracts.Controllers.AbstractGameBoardController;

public class TicTacToeController extends AbstractGameBoardController {
    @Override
    protected void createLabels() {
        this.player_1.setText("O " + this.getPlayerUsername());
        this.player_2.setText("X " + this.getOpponentUsername());
    }

    @Override
    protected HBox createRow(HBox row) {
        row.setPrefHeight(120.0);
        row.setPrefWidth(360.0);
        row.setSpacing(5.0);

        return row;
    }

    @Override
    protected Button createBtn(Button btn) {
        btn.setPrefHeight(120.0);
        btn.setPrefWidth(120.0);

        return btn;
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
}

