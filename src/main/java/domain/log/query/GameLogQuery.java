package domain.log.query;

import domain.log.table.GameLogTable;
import domain.player.model.Player;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;
import support.enums.GameEndStateEnum;

public class GameLogQuery extends AbstractQuery<GameLogQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameLogTable();
    }

    public GameLogQuery filterByPlayer(Player<?> player) {
        return this.where("player", "=", player.getId());
    }

    public GameLogQuery filterByOpponent(Player<?> opponent) {
        return this.where("opponent", "=", opponent.getId());
    }

    public GameLogQuery filterByState(GameEndStateEnum state) {
        return this.where("state", "=", state.toString());
    }

}
