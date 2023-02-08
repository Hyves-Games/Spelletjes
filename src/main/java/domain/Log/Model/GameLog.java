package Domain.Log.Model;

import Domain.Log.Table.GameLogTable;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractTable;
import Support.Enums.GameEndStateEnum;
import Support.Enums.GameEnum;
import Support.Enums.GameModeEnum;

import java.util.ArrayList;

public class GameLog extends AbstractModel<GameLog> {

    private GameModeEnum gameMode;
    private GameEnum game;
    private GameEndStateEnum state;
    private Player<?> player;
    private Player<?> opponent;
    private ArrayList<Integer> board = new ArrayList<>();

    @Override
    public AbstractTable getTable() {
        return new GameLogTable();
    }

    public GameLog() {}


    public GameModeEnum getGameMode() {
        return gameMode;
    }

    public GameLog setGameMode(GameModeEnum gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public GameEnum getGame() {
        return game;
    }

    public GameLog setGame(GameEnum game) {
        this.game = game;
        return this;
    }

    public GameEndStateEnum getState() {
        return state;
    }

    public GameLog setState(GameEndStateEnum state) {
        this.state = state;

        return this;
    }

    public Player<?> getPlayer() {
        return player;
    }

    public GameLog setPlayer(Player<?> player) {
        this.player = player;

        return this;
    }

    public Player<?> getOpponent() {
        return opponent;
    }

    public GameLog setOpponent(Player<?> opponent) {
        this.opponent = opponent;

        return this;
    }

    public ArrayList<Integer> getBoard() {
        return board;
    }

    public GameLog setBoard(ArrayList<Integer> board) {
        this.board = board;

        return this;
    }
}
