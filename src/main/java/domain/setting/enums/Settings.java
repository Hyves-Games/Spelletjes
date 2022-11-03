package domain.setting.enums;

import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;
import support.helpers.Utils;

import java.io.Serializable;
import java.sql.Timestamp;

public enum Settings {
    // server properties
    SERVER_IP_ADDRESS("server_ip_address","localhost"),
    SERVER_PORT("server_port", 7789),

    // audio properties
    MUSIC_VOLUME_LOBBY("music_volume_lobby", 0.1);

    private final String key;
    private final Serializable defaultValue;

    Settings(String key, Serializable defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public static Settings getEnum(String key) {
        for (Settings setting : Settings.values()) {
            if (setting.getKey().equals(key)) {
                return setting;
            }
        }

        return null;
    }

    public String getKey() {
        return key;
    }

    public Setting getSetting() {
        Setting setting = new SettingQuery().filterByName(key).findOne();
        if (setting == null) {
            setting = new Setting(key , defaultValue);
        }

        return setting;
    }

    public Serializable getDefaultValue() {
        return defaultValue;
    }

    public Serializable getValue() {
        Setting setting = new SettingQuery().filterByName(key).findOne();
        if (setting == null) {
            return defaultValue;
        }

        return setting.getValue();
    }

    public Boolean getBooleanValue() {
        return Utils.convertSerializableToBoolean(getValue());
    }

    public String getStringValue() {
        return Utils.convertSerializableToString(getValue());
    }

    public Integer getIntegerValue() {
        return Utils.convertSerializableToInteger(getValue());
    }

    public Double getDoubleValue() {
        return Utils.convertSerializableToDouble(getValue());
    }

    public Float getFloatValue() {
        return Utils.convertSerializableToFloat(this.getValue());
    }

    public Timestamp getTimestampValue() {
        return Utils.convertSerializableToTimestamp(this.getValue());
    }

    public void saveWithValue(Serializable value) {
        Setting setting = this.getSetting();
        if (setting.getValue() == value) {
            return;
        }

        setting.save();
    }
}
