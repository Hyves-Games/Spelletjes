package support.abstracts;

import domain.game.exceptions.MoveNotAllowedException;
import domain.player.model.AI;
import domain.player.model.Player;
import org.jetbrains.annotations.NotNull;
import support.actions.MoveServerAction;
import support.enums.GameEndStateEnum;
import support.enums.SceneEnum;
import support.services.Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGameBoard<T> {
    private Server connection;

    private Boolean useAI = false;
    private Boolean ended = false;
    private Boolean playerTurn = false;

    private Boolean isStarter = false;

    private Player<?> player;
    private Player<?> opponent;

    private GameEndStateEnum endState;

    private final ArrayList<Runnable> eventListenersForEnd = new ArrayList<Runnable>();
    private final ArrayList<Runnable> eventListenersForTurn = new ArrayList<Runnable>();
    private final ArrayList<Runnable> eventListenersForBoard = new ArrayList<Runnable>();

    protected final ArrayList<Integer> board = new ArrayList<Integer>();

    protected final void generate(Integer size) {
        for (int i = 0; i < size; i++) {
            this.board.add(0);
        }
    }

    public void start(@NotNull Player<?> player, @NotNull Player<?> opponent) {
        this.player = player;
        this.opponent = opponent;

        if (player instanceof AI) {
            this.useAI = true;
            this.connection = ((AI) player).getConnection();
        }
    }

    public Player<?> getPlayer() {
        return this.player;
    }

    public Player<?> getOpponent() {
        return this.opponent;
    }

    public Boolean isPlayerTurn() {
        return this.playerTurn;
    }

    public void setPlayerTurn() {
        this.playerTurn = true;

        if (this.useAI) {
            this.runAI();
        }

        this.runEventListeners(this.eventListenersForTurn);
    }
    public void setOpponentTurn() {
        this.playerTurn = false;

        this.runEventListeners(this.eventListenersForTurn);
    }

    public boolean getStarter() { return isStarter; }

    public void setStarter(Boolean starter) {
        isStarter = starter;
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
                if (this.useAI) {
                    new MoveServerAction(index, this.connection);
                } else {
                    new MoveServerAction(index);
                }

                this.setOpponentTurn();
            } catch (MoveNotAllowedException ignored) {}
        }
    }

    protected Boolean checkMove(Integer index) {
        return this.board.get(index) == 0;
    }

    public void setMove(Integer index, Integer value) {
        this.board.set(index, value);

        this.runLogic(index, value);

        this.runEventListeners(this.eventListenersForBoard);
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

    protected void runLogic(Integer index, Integer value) {}

    public abstract String getKey();

    public abstract SceneEnum getScene();

    public abstract String getIconPath();

    public abstract String getName();

    public abstract void runAI();
}
