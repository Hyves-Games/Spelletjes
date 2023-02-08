package Domain.Log.Query;

import Domain.Log.Table.GameSetLogTable;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class GameSetLogQuery extends AbstractQuery<GameSetLogQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameSetLogTable();
    }

}
