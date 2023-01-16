package domain.simulation.model;

import domain.player.model.Player;
import domain.simulation.table.SimulationRoundTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;

public class SimulationRound extends AbstractModel<SimulationRound> {

    private Integer totalRounds;
    private Player<?> playerOne;
    private Player<?> playerTwo;

    @Override
    public AbstractTable getTable() {
        return new SimulationRoundTable();
    }

    public SimulationRound() {}

    public Integer getTotalRounds() {
        return totalRounds;
    }

    public SimulationRound setTotalRounds(Integer totalRounds) {
        this.totalRounds = totalRounds;

        return this;
    }

    public Player<?> getPlayerOne() {
        return playerOne;
    }

    public SimulationRound setPlayerOne(Player<?> playerOne) {
        this.playerOne = playerOne;

        return this;
    }

    public Player<?> getPlayerTwo() {
        return playerTwo;
    }

    public SimulationRound setPlayerTwo(Player<?> playerTwo) {
        this.playerTwo = playerTwo;

        return this;
    }
}
