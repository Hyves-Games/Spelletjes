package domain.setting.query;

import domain.setting.enums.Settings;
import domain.setting.model.Setting;
import domain.setting.table.SettingTable;
import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;
import support.helpers.Utils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SettingQuery extends AbstractQuery<SettingQuery> {
    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    public SettingQuery filterByName(String value) {
        return this.where("name", "=", value);
    }

    @Override
    protected <M extends AbstractModel<M>> M getModelFromResultSet(ResultSet result) {
        try {
            String name = (String) result.getObject("name");

            Serializable value = (Serializable) result.getObject("value");

            switch (Settings.getEnum(name).getDefaultValue().getClass().getSimpleName()) {
                case "String" -> value = Utils.convertSerializableToString(value);
                case "Integer" -> value = Utils.convertSerializableToInteger(value);
                case "Float" -> value = Utils.convertSerializableToFloat(value);
                case "Double" -> value = Utils.convertSerializableToDouble(value);
                case "Boolean" -> value = Utils.convertSerializableToBoolean(value);
                case "Timestamp" -> value = Utils.convertSerializableToTimestamp(value);
            }

            return (M) new Setting(name, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
