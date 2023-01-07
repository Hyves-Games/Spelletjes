package domain.simulation.query;

import domain.log.table.GameSetLogTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

public class SimulationRoundQuery extends AbstractQuery<SimulationRoundQuery> {
    @Override
    public AbstractTable getTable() {
        return new GameSetLogTable();
    }

}
