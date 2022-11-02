package support.abstracts;

import domain.player.model.Player;

import java.util.ArrayList;

public abstract class AbstractGameBoard {
    protected Player player;
    protected Player opponent;
    protected Boolean yourTurn = false;
    protected ArrayList<Integer> board = new ArrayList<>();

    protected final void generate(Integer size) {
        for (int i = 0; i < size; i++) {
            this.board.add(0);
        }
    }

    public AbstractGameBoard setPlayer(Player player) {
        this.player = player;

        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public AbstractGameBoard setOpponent(Player opponent) {
        this.opponent = opponent;

        return this;
    }

    public Player getOpponent() {
        return opponent;
    }

    public AbstractGameBoard setYourTurn(Boolean condition) {
        this.yourTurn = condition;

        return this;
    }

    public Boolean isYourTurn() {
        return yourTurn;
    }

    public abstract String getIconPath();

    public abstract String getName();

    public abstract Boolean setMove(Integer index);

    protected abstract Boolean checkMove(Integer index);

    public ArrayList<Integer> getBoard() {
        return board;
    }

}
