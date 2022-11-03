package domain.setting.table;

import domain.setting.model.Setting;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;

public class SettingTable extends AbstractTable {

    private String name;
    private String value;

    @Override
    protected String getTableName() {
        return "settings";
    }

    @Override
    protected <T extends AbstractModel<T>> T getModel() {
        return (T) new Setting();
    }
}
