package Domain.Setting.Query;

import Domain.Setting.Table.SettingTable;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

public class SettingQuery extends AbstractQuery<SettingQuery> {
    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public SettingQuery filterByName(String value) {
        return this.where("name", "=", value);
    }
}
