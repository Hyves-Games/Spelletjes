package domain.game.model;

import client.Application;
import support.abstracts.AbstractGame;

import java.util.Objects;

public class TicTacToe extends AbstractGame {

    public static String name = "Tic Tac Toe";
    public static String iconPath = Application.class.getResource("assets/icons/tic_tac_toe.png").toString();

}
