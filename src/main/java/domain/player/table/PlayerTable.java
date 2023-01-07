package domain.player.table;

import domain.player.model.Player;
import domain.player.query.PlayerQuery;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;
import support.enums.GameStrategyEnum;

import java.sql.Timestamp;

public class PlayerTable extends AbstractTable {

    private String username;
    private GameStrategyEnum gameStrategy;
    private Timestamp lastLogin;

    @Override
    public String getTableName() {
        return "players";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new Player();
    }

    @Override
    public <T extends AbstractQuery<T>> T getQuery() {
        return (T) new PlayerQuery();
    }
}
