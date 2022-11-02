package domain.game.model;

import client.Application;

import domain.game.exceptions.MoveNotAllowedException;
import domain.player.model.Player;
import support.abstracts.AbstractGameBoard;
import support.actions.MoveServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;


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

    @Override
    public Boolean setMove(Integer index) {
        if (this.checkMove(index)) {
            try {
                new MoveServerAction(index);
            } catch (MoveNotAllowedException e) {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    protected Boolean checkMove(Integer index) {
        return this.board.get(index) == 0;
    }
}
