package domain.setting.enums;

import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;

import java.sql.SQLException;
import java.sql.Timestamp;

public enum Settings {
    // server properties
    SERVER_IP_ADDRESS("server_ip_address","localhost"),
    SERVER_PORT("server_port", "7789"),

    // audio properties
    MUSIC_VOLUME_LOBBY("music_volume_lobby", "0.1");

    private final String key;
    private final String defaultValue;

    Settings(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public Setting getSetting() {
        Setting setting = new SettingQuery().filterByName(key).findOne();
        if (setting == null) {
            setting = new Setting(key , defaultValue);
        }

        return setting;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValue() {
        Setting setting = new SettingQuery().filterByName(key).findOne();
        if (setting == null) {
            return defaultValue;
        }

        return setting.getValue();
    }

    public Boolean getBooleanValue() {
        return Boolean.parseBoolean(getValue());
    }

    public Integer getIntegerValue() {
        return Integer.parseInt(getValue());
    }

    public Double getDoubleValue() {
        return Double.parseDouble(getValue());
    }

    public Float getFloatValue() {
        return Float.parseFloat(getValue());
    }

    public Timestamp getTimestampValue() {
        return new Timestamp(Long.getLong(getValue()));
    }

    private void save(String value) {
        if (this.getValue().equals(value)) {
            return;
        }

        this.getSetting().setValue(value).save();
    }

    public void saveWithValue(String value) {
        this.save(value);
    }

    public void saveWithValue(Double value) {
        this.save(value.toString());
    }

    public void saveWithValue(Integer value) {
        this.save(value.toString());
    }

    public void saveWithValue(Float value) {
        this.save(value.toString());
    }

    public void saveWithValue(Boolean value) {
        this.save(value.toString());
    }

    public void saveWithValue(Timestamp value) {
        this.save(Float.toString(value.getTime()));
    }
}
