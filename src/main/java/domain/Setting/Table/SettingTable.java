package Domain.Setting.Table;

import Domain.Setting.Model.Setting;
import Domain.Setting.Query.SettingQuery;
import Support.Abstracts.AbstractModel;
import Support.Abstracts.AbstractQuery;
import Support.Abstracts.AbstractTable;

import java.io.Serializable;

public class SettingTable extends AbstractTable {

    private String name;
    private Serializable value;

    @Override
    public String getTableName() {
        return "settings";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new Setting();
    }

    @Override
    public <T extends AbstractQuery<T>> T getQuery() {
        return (T) new SettingQuery();
    }
}
