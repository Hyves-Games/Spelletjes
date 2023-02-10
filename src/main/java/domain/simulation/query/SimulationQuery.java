package domain.simulation.query;

import domain.simulation.table.SimulationTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

public class SimulationQuery extends AbstractQuery<SimulationQuery> {
    @Override
    public AbstractTable getTable() {
        return new SimulationTable();
    }

}
