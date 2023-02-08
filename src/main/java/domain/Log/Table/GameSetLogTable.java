package Domain.Log.Table;

import Domain.Log.Model.GameLog;
import Domain.Log.Model.GameSetLog;
import Domain.Log.Query.GameSetLogQuery;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

import java.util.ArrayList;

public class GameSetLogTable extends AbstractTable {

    private GameLog gameLog;
    private Player<?> player;
    private ArrayList<Integer> board;
    private Integer duration;

    @Override
    public String getTableName() {
        return "game_set_logs";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new GameSetLog();
    }

    @Override
    public <T extends AbstractQuery<T>> T getQuery() {
        return (T) new GameSetLogQuery();
    }
}
