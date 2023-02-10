package support.enums;

import domain.log.table.GameLogTable;
import domain.log.table.GameSetLogTable;
import domain.player.table.PlayerTable;
import domain.setting.table.SettingTable;
import domain.simulation.table.SimulationRoundTable;
import domain.simulation.table.SimulationTable;
import support.abstracts.AbstractTable;

import java.sql.SQLException;

public enum DatabaseTableEnum {
    SETTINGS(new SettingTable()),
    PLAYERS(new PlayerTable()),
    GAME_LOG(new GameLogTable()),
    GAME_SET_LOG(new GameSetLogTable()),
    SIMULATION_ROUND(new SimulationRoundTable()),
    SIMULATION(new SimulationTable());

    private final AbstractTable table;

    DatabaseTableEnum(AbstractTable table) {
        this.table = table;
    }

    public AbstractTable getTable() {
        return this.table;
    }

    public static void createTables() throws SQLException {
//        // remove all tables
//        for (DatabaseTableEnum table : DatabaseTableEnum.values()) {
//            table.getTable().dropTable();
//        }

        for (DatabaseTableEnum table : DatabaseTableEnum.values()) {
            table.getTable().createTable();
        }
    }
}
