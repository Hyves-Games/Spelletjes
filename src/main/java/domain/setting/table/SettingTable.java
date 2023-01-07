package domain.setting.table;

import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

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
