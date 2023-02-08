package Domain.Log.Model;

import Domain.Log.Table.GameSetLogTable;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractTable;

import java.util.ArrayList;

public class GameSetLog extends AbstractModel<GameSetLog> {

    private GameLog gameLog;
    private Player<?> player;
    private ArrayList<Integer> board;
    private Integer duration;

    @Override
    public AbstractTable getTable() {
        return new GameSetLogTable();
    }

    public GameSetLog() {}

    public GameLog getGameLog() {
        return gameLog;
    }

    public GameSetLog setGameLog(GameLog gameLog) {
        this.gameLog = gameLog;

        return this;
    }

    public Player<?> getPlayer() {
        return player;
    }

    public GameSetLog setPlayer(Player<?> player) {
        this.player = player;

        return this;
    }

    public ArrayList<Integer> getBoard() {
        return board;
    }

    public GameSetLog setBoard(ArrayList<Integer> board) {
        this.board = board;

        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    // duration in ms
    public GameSetLog setDuration(Integer duration) {
        this.duration = duration;

        return this;
    }
}
