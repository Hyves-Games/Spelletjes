package Support.Enums;

import Domain.Log.Table.GameLogTable;
import Domain.Log.Table.GameSetLogTable;
import Domain.Player.Table.PlayerTable;
import Domain.Setting.Table.SettingTable;
import Domain.Simulation.Table.SimulationRoundTable;
import Domain.Simulation.Table.SimulationTable;
import Support.Abstracts.AbstractTable;

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
