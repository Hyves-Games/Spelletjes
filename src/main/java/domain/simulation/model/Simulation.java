package domain.simulation.model;

import domain.log.model.GameLog;
import domain.simulation.table.SimulationTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;

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
