package Domain.Player.Table;

import Domain.Player.Model.Player;
import Domain.Player.Query.PlayerQuery;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;
import Support.Enums.GameStrategyEnum;

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
