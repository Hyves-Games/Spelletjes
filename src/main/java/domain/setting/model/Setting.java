package domain.setting.model;

import domain.setting.enums.Settings;
import domain.setting.table.SettingTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractTable;
import support.helpers.Utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class Setting extends AbstractModel<Setting> {

    private String name;
    private Serializable value;

    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public Setting() {}

    public Setting(String name, Serializable value) {
        this.name = name;

        ArrayList<Class> allowedTypes = new ArrayList<>(Arrays.asList(
                String.class,
                Integer.class,
                Float.class,
                Double.class,
                Boolean.class,
                Timestamp.class
        ));
        if (allowedTypes.contains(value.getClass())) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("The type of the value is not allowed.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Setting setValue(Serializable value) {
        this.value = value;

        return this;
    }

    public Serializable getDefaultValue() {
        return Settings.valueOf(this.name).getDefaultValue();
    }

    public Serializable getValue() {
        return this.value;
    }

    public String getStringValue() {
        return Utils.convertSerializableToString(this.getValue());
    }

    public Integer getIntegerValue() {
        return Utils.convertSerializableToInteger(this.getValue());
    }

    public Double getDoubleValue() {
        return Utils.convertSerializableToDouble(this.getValue());
    }

    public Float getFloatValue() {
        return Utils.convertSerializableToFloat(this.getValue());
    }

    public Boolean getBooleanValue() {
        return Utils.convertSerializableToBoolean(this.getValue());
    }

    public Timestamp getTimestampValue() {
        return Utils.convertSerializableToTimestamp(this.getValue());
    }
}
