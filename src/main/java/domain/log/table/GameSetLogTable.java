package domain.log.table;

import domain.log.model.GameLog;
import domain.log.model.GameSetLog;
import domain.log.query.GameSetLogQuery;
import domain.player.model.Player;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

import java.util.ArrayList;

public class GameSetLogTable extends AbstractTable {

    private GameLog gameLog;
    private Player<?> player;
    private ArrayList<Integer> board;
    private Integer duration;

    @Override
    protected String getTableName() {
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
