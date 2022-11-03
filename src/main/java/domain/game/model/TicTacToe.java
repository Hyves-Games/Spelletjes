package domain.game.model;

import client.Application;

import support.abstracts.AbstractGameBoard;

public class TicTacToe extends AbstractGameBoard {
    public TicTacToe() {
        this.generate(9);
    }

    @Override
    public String getName() {
        return "Tic Tac Toe";
    }

    @Override
    public String getIconPath() {
        return Application.class.getResource("assets/icons/tic_tac_toe.png").toString();
    }
}
