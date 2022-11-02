package support.abstracts;

import domain.player.model.Player;

import java.util.ArrayList;

public abstract class AbstractGameBoard {
    protected ArrayList<Integer> board = new ArrayList<>();

    protected final void generate(Integer size) {
        for (int i = 0; i < size; i++) {
            this.board.add(0);
        }
    }

    public Player player;

    public Player opponent;

    public abstract String getIconPath();

    public abstract String getName();

    public ArrayList<Integer> getBoard() {
        return board;
    }

    public String playerToMove;

}
