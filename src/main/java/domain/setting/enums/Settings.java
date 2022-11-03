package domain.setting.enums;

import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;
import support.helpers.Utils;

import java.io.Serializable;
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

    public void saveWithValue(Serializable value) {
        value = Utils.getValueForSerializable(value);

        if (this.getSetting().getValue() == value) {
            return;
        }

        this.setting.save();
    }
}
