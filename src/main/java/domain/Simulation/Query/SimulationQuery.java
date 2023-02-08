package Domain.Simulation.Query;

import Domain.Simulation.Table.SimulationTable;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class SimulationQuery extends AbstractQuery<SimulationQuery> {
    @Override
    public AbstractTable getTable() {
        return new SimulationTable();
    }

}
