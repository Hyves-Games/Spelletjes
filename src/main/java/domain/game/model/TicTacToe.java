package domain.game.model;

import client.Application;
import support.abstracts.AbstractGame;

import java.util.Objects;

public class TicTacToe extends AbstractGame {

    public TicTacToe() {
        this.name = "Tic Tac Toe";
        this.gameModes = new String[] {"Player vs Player", "Player vs AI", "AI vs AI"};
        this.iconPath = Objects.requireNonNull(Application.class.getResource("assets/icons/tic_tac_toe.png")).toString();
        this.musicPath = "focking spannende muziek.mp3";
    }

}
