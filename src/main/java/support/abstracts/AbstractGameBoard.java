package support.abstracts;

import domain.game.exceptions.MoveNotAllowedException;
import domain.player.model.Player;
import support.actions.MoveServerAction;
import support.enums.GameEndStateEnum;
import support.exceptions.NoServerConnectionException;

import java.util.ArrayList;

public abstract class AbstractGameBoard {
    private Player player;
    private Player opponent;

    private Boolean ended = false;
    private Boolean playerTurn = false;

    private GameEndStateEnum endState;

    private Runnable eventListenerForEnd;
    private Runnable eventListenerForTurn;
    private Runnable eventListenerForBoard;

    private final ArrayList<Integer> board = new ArrayList<>();

    protected final void generate(Integer size) {
        for (int i = 0; i < size; i++) {
            this.board.add(0);
        }
    }

    public void start(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public Object[] getBoard() {
        return this.board.toArray();
    }

    public Boolean isPlayerTurn() {
        return this.playerTurn;
    }

    public void setPlayerTurn() {
        this.playerTurn = true;

        this.eventListenerForTurn.run();
    }

    public void setOpponentTurn() {
        this.playerTurn = false;

        this.eventListenerForTurn.run();
    }

    public Boolean isGameEnded() {
        return this.ended;
    }

    public void setGameEnd() {
        this.ended = true;

        this.eventListenerForEnd.run();
    }

    public GameEndStateEnum getEndState() {
        return endState;
    }

    public void setEndState(GameEndStateEnum endState) {
        this.endState = endState;
    }

    public void doMove(Integer index) {
        if (this.checkMove(index)) {
            try {
                new MoveServerAction(index);

                this.setOpponentTurn();
            } catch (MoveNotAllowedException | NoServerConnectionException ignored) {}
        }
    }

    public void setMove(Integer index, Integer value) {
        this.board.set(index, value);
        this.eventListenerForBoard.run();

        if (value == -1) {
            this.setPlayerTurn();
            this.eventListenerForTurn.run();
        }
    }

    protected Boolean checkMove(Integer index) {
        return this.board.get(index) == 0;
    }

    public void setEventListenerForTurn(Runnable callback) {
        this.eventListenerForTurn = callback;
    }

    public void setEventListenerForBoard(Runnable callback) {
        this.eventListenerForBoard = callback;
    }

    public void setEventListenerForEnd(Runnable callback) {
        this.eventListenerForEnd = callback;
    }

    public abstract String getIconPath();

    public abstract String getName();
}
