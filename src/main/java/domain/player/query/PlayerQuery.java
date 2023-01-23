package domain.player.query;

import domain.player.model.Player;
import domain.player.table.PlayerTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;
import support.enums.GameStrategyEnum;

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

    public Player<?> findOneOrCreate(String username) {
        this.filterByName(username);

        Player<?> p = (Player<?>) this.findOneOrCreate();

        if (p.isNew()) {
            p.setUsername(username);
        }

        return p;
    }
}
