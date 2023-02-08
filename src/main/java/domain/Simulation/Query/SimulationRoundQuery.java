package Domain.Simulation.Query;

import Domain.Log.Table.GameSetLogTable;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class SimulationRoundQuery extends AbstractQuery<SimulationRoundQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameSetLogTable();
    }

}
