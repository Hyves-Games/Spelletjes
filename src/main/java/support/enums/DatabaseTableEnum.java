package support.enums;

import domain.setting.table.SettingTable;
import support.abstracts.AbstractTable;

import java.sql.SQLException;

public enum DatabaseTableEnum {
    SETTINGS(new SettingTable());

    private final AbstractTable table;

    DatabaseTableEnum(AbstractTable table) {
        this.table = table;
    }

    public AbstractTable getTable() {
        return this.table;
    }

    public static void createTables() throws SQLException {
        for (DatabaseTableEnum table : DatabaseTableEnum.values()) {
            table.getTable().createTable();
        }
    }
}
