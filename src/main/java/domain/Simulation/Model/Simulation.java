package Domain.Simulation.Model;

import Domain.Log.Model.GameLog;
import Domain.Simulation.Table.SimulationTable;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractTable;

public class Simulation extends AbstractModel<Simulation> {

    private SimulationRound simulationRound;
    private GameLog gameLog;
    private Integer duration;

    @Override
    public AbstractTable getTable() {
        return new SimulationTable();
    }

    public Simulation() {}

    public SimulationRound getSimulationRound() {
        return simulationRound;
    }

    public Simulation setSimulationRound(SimulationRound simulationRound) {
        this.simulationRound = simulationRound;

        return this;
    }

    public GameLog getGameLog() {
        return gameLog;
    }

    public Simulation setGameLog(GameLog gameLog) {
        this.gameLog = gameLog;

        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public Simulation setDuration(Integer duration) {
        this.duration = duration;

        return this;
    }
}
