package domain.game.model;

import client.Application;
import client.game.board.controller.TicTacToeController;
import domain.player.model.Player;
import support.abstracts.AbstractGameBoard;
import support.helpers.Auth;

public class TicTacToe extends AbstractGameBoard {

    public TicTacToe() {
        this.generate(9);
        this.player = Auth.getPlayer();
        this.opponent = new Player("Lech", false);

        //Dit krijg je van de server uiteindelijk
        this.playerToMove = opponent.getUsername();
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
