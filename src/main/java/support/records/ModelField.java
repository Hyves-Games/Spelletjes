package support.records;

import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;
import support.helpers.Utils;

import java.io.Serializable;
import java.lang.reflect.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public record ModelField(Field field, AbstractModel<?> model) {

    public ModelField(Field field, AbstractModel<?> model) {
        try {
            try {
                field = model.getClass().getDeclaredField(field.getName());
            } catch (NoSuchFieldException e) {
                field = model.getClass().getSuperclass().getDeclaredField(field.getName());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        this.field = field;
        this.model = model;

        this.field.setAccessible(true);
    }

    public String databaseName() {
        return Utils.camelCaseToUnderscore(this.field.getName());
    }

    public String getName() {
        return this.field.getName();
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public String getTypeName() {
        return this.getType().getSimpleName();
    }

    public Boolean isArrayList() {
        return this.getTypeName().equals("ArrayList");
    }

    public Boolean isString() {
        return this.getTypeName().equals("String");
    }

    public Boolean isInteger() {
        return this.getTypeName().equals("Integer");
    }

    public Boolean isBoolean() {
        return this.getTypeName().equals("Boolean");
    }

    public boolean isSerializable() {
        return this.getTypeName().equals("Serializable");
    }

    private void setArrayList(String string) throws IllegalAccessException {
        if (string.isEmpty()) {
            return;
        }

        String[] arrayItems = string.substring(1, string.length() - 1).split(",");

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

        if (listClass == Integer.class) {
            ArrayList<Integer> list = new ArrayList<>() {{
                for (String item : arrayItems) {
                    add(Integer.parseInt(item.trim()));
                }
            }};

            this.field.set(model, list);
        } else {
            ArrayList<String> list = new ArrayList<>() {{
                this.addAll(Arrays.asList(arrayItems));
            }};

            this.field.set(model, list);
        }
    }

    public Boolean isTimestamp() {
        return this.getTypeName().equals("Timestamp");
    }

    public Boolean isEnum() {
        return this.getType().isEnum();
    }

    private void setEnumValue(String value) throws IllegalAccessException {
        if (value == null) {
            return;
        }

        this.field.set(this.model, createEnumInstance(value, this.getType()));
    }

    @SuppressWarnings("unchecked")
    private <A extends Enum<A>> A createEnumInstance(String name, Type type) {
        return Enum.valueOf((Class<A>) type, name);
    }

    public Boolean isModel() {
        Class<?> typeClass = this.field.getType();

        return typeClass.getSuperclass() != null && typeClass.getSuperclass().getName().equals(AbstractModel.class.getName());
    }

    public AbstractTable getTable() {
        Class<?> typeClass = this.field.getType();

        try {
            Method method = typeClass.getMethod("getTable");

            return (AbstractTable) method.invoke(typeClass.newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public AbstractQuery<?> getQuery() {
        AbstractTable table = this.getTable();
        if (table != null) {
            return table.getQuery();
        }

        return null;
    }

    private void setModelValue(Integer id) throws IllegalAccessException {
        AbstractQuery<?> query = this.getQuery();

        if (query != null) {
            this.field.set(model, query.findOneById(id));
        }
    }

    public void setValue(ResultSet resultSet) {
        try {
            if (this.isEnum()) {
                this.setEnumValue(resultSet.getString(this.databaseName()));
            } else if (this.isModel()) {
                this.setModelValue(resultSet.getInt(this.databaseName()));
            } else if (this.isTimestamp()) {
                this.field.set(this.model, resultSet.getTimestamp(this.databaseName()));
            } else if (this.isArrayList()) {
                this.setArrayList(resultSet.getString(this.databaseName()));
            } else {
                this.field.set(this.model, resultSet.getObject(this.databaseName()));
            }
        } catch (IllegalAccessException|SQLException e) {
            e.printStackTrace();
        }
    }

    public Object getValue() {
        try {
            return this.field.get(this.model);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSqlForTableCreation() {
        return ", " + databaseName() + " " + this.getSqlType();
    }

    public String getSqlType() {
        HashMap<String, String> converted = new HashMap<>() {{
            put("String", "text");
            put("ArrayList", "text");
            put("Timestamp", "TIMESTAMP");
            put("Model", "integer");
        }};

        String type = this.getTypeName();

        if (this.isEnum()) {
            return this.convertEnumToSqlType();
        }

        if (this.isModel()) {
            type = "Model";
        }

        type = converted.get(type) != null ? converted.get(type) : type;

        return type;
    }

    public String getForeignKeyConstraint() {
        if (!this.isModel()) {
            return null;
        }

        return "CONSTRAINT fk_"
                + databaseName()
                + " FOREIGN KEY ("
                + databaseName()
                + ") REFERENCES "
                + Objects.requireNonNull(getTable()).getTableName()
                + "(id) ON DELETE CASCADE";
    }

    private String convertEnumToSqlType() {
        Object[] enums = this.getType().getEnumConstants();

        StringBuilder enumString = new StringBuilder(
                "text check( " + this.databaseName() + " in ("
        );

        for (Object e : enums) {
            enumString.append("'").append(e.toString()).append("',");
        }
        enumString.deleteCharAt(enumString.length() - 1);

        enumString.append("))");

        return enumString.toString();
    }

    public String getInsertType() {
        if (this.getValue() == null) {
            return "Null";
        }

        if (this.isEnum()) {
            return "String";
        }

        if (this.isModel()) {

            if (this.getModelId() == null) {
                return "Null";
            }

            return "ForeignKey";
        }

        return this.getTypeName();
    }

    public PreparedStatement prepareStatement(PreparedStatement preparedStatement, int index) throws SQLException {
        switch (this.getInsertType()) {
            case "String" -> preparedStatement.setString(index, (String) this.getValue().toString());
            case "Integer" -> preparedStatement.setInt(index, (Integer) this.getValue());
            case "Boolean" -> preparedStatement.setBoolean(index, (Boolean) this.getValue());
            case "Timestamp" -> preparedStatement.setTimestamp(index, (Timestamp) this.getValue());
            case "Serializable", "ArrayList" -> preparedStatement.setString(index, Utils.convertSerializableToString((Serializable) this.getValue()));
            case "ForeignKey" -> preparedStatement.setInt(index, this.getModelId());
            case "Null" -> preparedStatement.setNull(index, 0);
            default -> throw new RuntimeException("Unsupported field type: " + field.getType().getSimpleName());
        }

        return preparedStatement;
    }

    public Integer getModelId() {
        try {
            AbstractModel<?> foreignModel = ((AbstractModel<?>) this.field.get(model));
            if (foreignModel != null) {
                if (foreignModel.isNew()) {
                    foreignModel.save();
                }

                return foreignModel.getId();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
