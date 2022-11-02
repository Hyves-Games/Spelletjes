package domain.setting.query;

import domain.setting.model.Setting;
import domain.setting.table.SettingTable;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

import java.sql.SQLException;

public class SettingQuery extends AbstractQuery {
    @Override
    public AbstractTable getTable() {
        return new SettingTable();
    }

    @Override
    public Setting[] find() {
        return (Setting[]) super.find();
    }

    @Override
    public Setting findOneById(int id) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return (Setting) super.findOneById(id);
    }
}
