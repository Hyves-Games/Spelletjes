package domain.setting.model;

import domain.setting.table.SettingTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;
import support.helpers.Utils;

import java.io.Serializable;
import java.sql.Timestamp;

public class Setting extends AbstractModel<Setting> {

    private String name;
    private String value;

    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public Setting() {}

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Setting setValue(Serializable value) {
        this.value = Utils.getValueForSerializable(value);

        return this;
    }

    public String getValue() {
        return value;
    }

    public Integer getIntegerValue() {
        return Integer.parseInt(this.getValue());
    }

    public Double getDoubleValue() {
        return Double.parseDouble(this.getValue());
    }

    public Float getFloatValue() {
        return Float.parseFloat(this.getValue());
    }

    public Boolean getBooleanValue() {
        return Boolean.parseBoolean(this.getValue());
    }

    public Timestamp getTimestampValue() {
        return new Timestamp(Long.parseLong(this.getValue()));
    }
}
