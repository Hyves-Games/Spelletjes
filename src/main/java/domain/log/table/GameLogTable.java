package domain.log.table;

import domain.log.model.GameLog;
import domain.log.query.GameLogQuery;
import domain.player.model.Player;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;
import support.enums.GameEndStateEnum;
import support.enums.GameEnum;
import support.enums.GameModeEnum;

import java.util.ArrayList;

public class GameLogTable extends AbstractTable {

    private GameModeEnum gameMode;
    private GameEnum game;
    private GameEndStateEnum state;
    private Player<?> player;
    private Player<?> opponent;
    private ArrayList<Integer> board;

    @Override
    protected String getTableName() {
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
