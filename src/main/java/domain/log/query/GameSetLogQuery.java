package domain.log.query;

import domain.log.table.GameSetLogTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

public class GameSetLogQuery extends AbstractQuery<GameSetLogQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameSetLogTable();
    }

}
