package client.game.board.controller;

import domain.game.model.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import support.abstracts.AbstractGameBoard;

public class TicTacToeController {

    @FXML ProgressBar timeLeft;

    private final TicTacToe ticTacToe = new TicTacToe();

    public void onMoveClick(ActionEvent event) {
        String index = ((Node)(event.getSource())).getId().split("_")[1];
        System.out.println(index);
    }

    public void onLeaveClick(ActionEvent event) {
        
    }
}
