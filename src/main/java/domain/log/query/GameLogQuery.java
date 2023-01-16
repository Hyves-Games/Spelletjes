package domain.log.query;

import domain.log.table.GameLogTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

public class GameLogQuery extends AbstractQuery<GameLogQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameLogTable();
    }

}
