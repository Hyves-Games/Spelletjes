package client.game.board.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class TicTacToeController {
    public void onMoveClick(ActionEvent event) {
        String index = ((Node)(event.getSource())).getId().split("_")[1];
        System.out.println(index);

    }

    public void onLeaveClick(ActionEvent event) {
        
    }
}
