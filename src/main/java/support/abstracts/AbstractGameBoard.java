package support.abstracts;

import domain.game.exceptions.MoveNotAllowedException;
import domain.player.model.AI;
import domain.player.model.Player;
import support.actions.MoveServerAction;
import support.enums.GameEndStateEnum;
import support.enums.SceneEnum;

import java.util.ArrayList;

public abstract class AbstractGameBoard {
    private Player player;
    private Player opponent;

    private Boolean ended = false;
    private Boolean playerTurn = false;

    private GameEndStateEnum endState;

    private final ArrayList<Integer> board = new ArrayList<Integer>();

    private final ArrayList<Runnable> eventListenersForEnd = new ArrayList<Runnable>();
    private final ArrayList<Runnable> eventListenersForTurn = new ArrayList<Runnable>();
    private final ArrayList<Runnable> eventListenersForBoard = new ArrayList<Runnable>();

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

    public Boolean isPlayerTurn() {
        return this.playerTurn;
    }

    public void setPlayerTurn() {
        this.playerTurn = true;

        if (this.player instanceof AI) {
            this.player.getGameBoard().runAI();
        }

        this.runEventListeners(this.eventListenersForTurn);
    }

    public void setOpponentTurn() {
        this.playerTurn = false;

        this.runEventListeners(this.eventListenersForTurn);
    }

    public Integer[] getBoard() {
        return this.board.toArray(new Integer[0]);
    }

    public Boolean isGameEnded() {
        return this.ended;
    }

    public void setGameEnd() {
        this.ended = true;

        this.runEventListeners(this.eventListenersForEnd);
    }

    public GameEndStateEnum getEndState() {
        return endState;
    }

    public void setEndState(GameEndStateEnum endState) {
        this.endState = endState;
    }

    public void doMove(Integer index) {
        if (this.checkMove(index) && this.isPlayerTurn()) {
            try {
                if (this.player instanceof AI) {
                    new MoveServerAction(index, ((AI) this.player).getConnection());
                } else {
                    new MoveServerAction(index);
                }

                this.setOpponentTurn();
            } catch (MoveNotAllowedException ignored) {}
        }
    }

    public void setMove(Integer index, Integer value) {
        this.board.set(index, value);

        this.runEventListeners(this.eventListenersForBoard);
    }

    protected Boolean checkMove(Integer index) {
        return this.board.get(index) == 0;
    }

    public void addEventListenerForEnd(Runnable callback) {
        this.eventListenersForEnd.add(callback);
    }

    public void addEventListenerForTurn(Runnable callback) {
        this.eventListenersForTurn.add(callback);
    }

    public void addEventListenerForBoard(Runnable callback) {
        this.eventListenersForBoard.add(callback);
    }

    private void runEventListeners(ArrayList<Runnable> callbacks) {
        callbacks.forEach(Runnable::run);
    }

    public abstract String getKey();

    public abstract SceneEnum getScene();

    public abstract String getIconPath();

    public abstract String getName();

    public abstract void runAI();
}
