package domain.setting.model;

import domain.setting.table.SettingTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;

import java.sql.Timestamp;

public class Setting extends AbstractModel<Setting> {

    private String name;
    private String value;

    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public Setting() {}

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

    public Setting setValue(String value) {
        this.value = value;

        return this;
    }

    public Setting setIntegerValue(Integer value) {
        this.value = value.toString();

        return this;
    }

    public Setting setDoubleValue(Double value) {
        this.value = value.toString();

        return this;
    }

    public Setting setFloatValue(Float value) {
        this.value = value.toString();

        return this;
    }

    public Setting setBooleanValue(Boolean value) {
        this.value = value.toString();

        return this;
    }

    public Setting setTimestampValue(Timestamp value) {
        this.value = Float.toString(value.getTime());

        return this;
    }

    public String getValue() {
        return value;
    }

    public Integer getIntegerValue() {
        return Integer.parseInt(value);
    }

    public Double getDoubleValue() {
        return Double.parseDouble(value);
    }

    public Float getFloatValue() {
        return Float.parseFloat(value);
    }

    public Boolean getBooleanValue() {
        return Boolean.parseBoolean(value);
    }

    public Timestamp getTimestampValue() {
        return new Timestamp(Long.parseLong(value));
    }
}
