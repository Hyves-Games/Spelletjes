package domain.simulation.table;

import domain.log.model.GameLog;
import domain.player.model.Player;
import domain.simulation.model.Simulation;
import domain.simulation.model.SimulationRound;
import domain.simulation.query.SimulationQuery;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

public class SimulationTable extends AbstractTable {

    private SimulationRound simulationRound;
    private GameLog gameLog;
    private Integer duration;

    @Override
    protected String getTableName() {
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
