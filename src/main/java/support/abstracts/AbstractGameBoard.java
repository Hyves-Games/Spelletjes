package support.abstracts;

import domain.game.exceptions.MoveNotAllowedException;
import domain.log.helpers.LogHandler;
import domain.log.model.GameLog;
import domain.player.model.AI;
import domain.player.model.Player;
import org.jetbrains.annotations.NotNull;
import support.actions.MoveServerAction;
import support.enums.GameEndStateEnum;
import support.enums.GameEnum;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;
import support.services.Server;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class AbstractGameBoard<T> {
    private Server connection;

    private Boolean useAI = false;
    private Boolean playerTurn = false;

    private Boolean starter = false;

    private Player<?> player;
    private Player<?> opponent;

    private GameEndStateEnum endState;

    private final ArrayList<Runnable> eventListenersForEnd = new ArrayList<>();
    private final ArrayList<Runnable> eventListenersForTurn = new ArrayList<>();
    private final ArrayList<Runnable> eventListenersForBoard = new ArrayList<>();

    protected final ArrayList<Integer> board = new ArrayList<>();

    public void start(@NotNull Player<?> player, @NotNull Player<?> opponent) {
        this.player = player;
        this.opponent = opponent;

        if (player instanceof AI) {
            this.useAI = true;
            this.connection = ((AI) player).getConnection();
        }

        for (int i = 0; i < this.getSize(); i++) {
            this.board.add(0);
        }
    }

    public void setStarter(Boolean starter) {
        this.starter = starter;
    }

    public boolean isStarter() { return starter; }

    public Integer getSize() {
        return this.getSizeX() * this.getSizeY();
    }

    public Player<?> getPlayer() {
        return this.player;
    }

    public Player<?> getOpponent() {
        return this.opponent;
    }

    public Integer[] getBoard() {
        return this.board.toArray(new Integer[0]);
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

    public void setGameEnd() {
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
                this.setOpponentTurn();

                if (this.useAI) {
                    new MoveServerAction(index, this.connection);
                } else {
                    new MoveServerAction(index);
                }

//                this.setOpponentTurn();
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

    public abstract Integer getSizeX();

    public abstract Integer getSizeY();

    public abstract String getKey();

    public abstract String getName();

    public abstract SceneEnum getScene();

    public abstract String getIconPath();

    public abstract void runAI();

    protected void runLogic(Integer index, Integer value) {}
}
