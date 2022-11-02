package domain.setting.model;

import domain.setting.table.SettingTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;

public class Setting extends AbstractModel {

    private String name;
    private String value;

    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public Setting() {
        super();
    }

    public Setting(
            String name,
            String value
    ) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
