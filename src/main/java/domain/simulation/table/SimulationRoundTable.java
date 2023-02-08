package domain.simulation.table;

import domain.player.model.Player;
import domain.simulation.model.SimulationRound;
import domain.simulation.query.SimulationRoundQuery;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

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
