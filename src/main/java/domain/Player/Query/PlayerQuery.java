package Domain.Player.Query;

import Domain.Player.Table.PlayerTable;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;
import Support.Enums.GameStrategyEnum;

public class PlayerQuery extends AbstractQuery<PlayerQuery> {
    @Override
    public AbstractTable getTable() {
        return new PlayerTable();
    }

    public AbstractQuery<PlayerQuery> filterByName(String value) {
        return this.where("username", "=", value);
    }

    public AbstractQuery<PlayerQuery> filterByGameStrategy(GameStrategyEnum strategyEnum) {
        return this.where("game_strategy", "=", strategyEnum.toString());
    }
}
