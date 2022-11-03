package domain.setting.query;

import domain.setting.table.SettingTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

public class SettingQuery extends AbstractQuery<SettingQuery> {
    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public SettingQuery filterByName(String value) {
        return this.where("name", "=", value);
    }
}
