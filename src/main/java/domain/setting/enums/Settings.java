package domain.setting.enums;

import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;

import java.sql.Timestamp;

public enum Settings {
    // server properties
    SERVER_IP_ADDRESS("server_ip_address","localhost"),
    SERVER_PORT("server_port", "7789"),

    // audio properties
    MUSIC_VOLUME_LOBBY("music_volume_lobby", "0.1");

    private final String key;
    private final String defaultValue;
    private final Setting setting;

    Settings(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.setting = this.getSetting();
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
        return this.getSetting().getBooleanValue();
    }

    public Integer getIntegerValue() {
        return this.getSetting().getIntegerValue();
    }

    public Double getDoubleValue() {
        return this.getSetting().getDoubleValue();
    }

    public Float getFloatValue() {
        return this.getSetting().getFloatValue();
    }

    public Timestamp getTimestampValue() {
        return this.getSetting().getTimestampValue();
    }

    private void save() {
        if (this.getValue().equals(this.setting.getValue())) {
            return;
        }

        this.setting.save();
    }

    public void saveWithValue(String value) {
        this.setting.setValue(value);

        this.save();
    }

    public void saveWithValue(Double value) {
        this.setting.setDoubleValue(value);

        this.save();
    }

    public void saveWithValue(Integer value) {
        this.setting.setIntegerValue(value);

        this.save();
    }

    public void saveWithValue(Float value) {
        this.setting.setFloatValue(value);
        this.save();
    }

    public void saveWithValue(Boolean value) {
        this.setting.setBooleanValue(value);

        this.save();
    }

    public void saveWithValue(Timestamp value) {
        this.setting.setTimestampValue(value);

        this.save();
    }
}
