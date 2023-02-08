package Domain.Log.Table;

import Domain.Log.Model.GameLog;
import Domain.Log.Query.GameLogQuery;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;
import Support.Enums.GameEndStateEnum;
import Support.Enums.GameEnum;
import Support.Enums.GameModeEnum;

import java.util.ArrayList;

public class GameLogTable extends AbstractTable {

    private GameModeEnum gameMode;
    private GameEnum game;
    private GameEndStateEnum state;
    private Player<?> player;
    private Player<?> opponent;
    private ArrayList<Integer> board;

    @Override
    public String getTableName() {
        return "game_logs";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new GameLog();
    }

    @Override
    public <T extends AbstractQuery<T>> T getQuery() {
        return (T) new GameLogQuery();
    }
}
