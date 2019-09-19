package com.antiy.asset.vo.enums.handlers;

import com.antiy.asset.vo.enums.ValuedEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义枚举属性转换器
 * @author pingd
 */
public class ValuedEnumTypeHandler<E extends Enum<E> & ValuedEnum>
    extends BaseTypeHandler<E> {
    private final Class<E> type;
    private Map<Integer, E> map = new HashMap<>();

    public ValuedEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }

        for (E e : enums) {
            map.put(e.getValue(), e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return getValuedEnum(i);
        }
    }

    private E getValuedEnum(int value) {
        E result = map.get(value);
        if (result == null) {
            throw new IllegalArgumentException(
                "Cannot convert " + value + " to " + type.getSimpleName() + " by value.");
        }
        return result;
    }
}
