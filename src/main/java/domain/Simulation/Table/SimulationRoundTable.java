package Domain.Simulation.Table;

import Domain.Player.Model.Player;
import Domain.Simulation.Model.SimulationRound;
import Domain.Simulation.Query.SimulationRoundQuery;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class SimulationRoundTable extends AbstractTable {

    private Integer totalRounds;
    private Player<?> playerOne;
    private Player<?> playerTwo;

    @Override
    public String getTableName() {
        return "simulation_rounds";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new SimulationRound();
    }

    @Override
    public <T extends AbstractQuery<T>> T getQuery() {
        return (T) new SimulationRoundQuery();
    }
}
