package Domain.Simulation.Table;

import Domain.Log.Model.GameLog;
import Domain.Simulation.Model.Simulation;
import Domain.Simulation.Model.SimulationRound;
import Domain.Simulation.Query.SimulationQuery;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class SimulationTable extends AbstractTable {

    private SimulationRound simulationRound;
    private GameLog gameLog;
    private Integer duration;

    @Override
    public String getTableName() {
        return "simulations";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new Simulation();
    }

    @Override
    public <T extends AbstractQuery<T>> T getQuery() {
        return (T) new SimulationQuery();
    }
}
