package Domain.Log.Query;

import Domain.Log.Table.GameLogTable;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class GameLogQuery extends AbstractQuery<GameLogQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameLogTable();
    }

}
