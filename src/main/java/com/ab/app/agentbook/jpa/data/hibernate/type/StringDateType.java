package com.ab.app.agentbook.jpa.data.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * 使用字符串来存储日期数据的存储格式 <br>
 *
 */
public class StringDateType implements UserType, ParameterizedType {
    public static final StringDateType INSTANCE = new StringDateType();

    public static final String FORMAT = "format";
    private String format;
    protected int[] sqlTypes = new int[] { Types.VARCHAR };

    @Override
    public void setParameterValues(Properties parameters) {
        format = parameters == null ? null : parameters.getProperty(FORMAT);
        if (format == null || "".equals(format)) {
            format = "yyyyMMddHHmmssSSSZ";
        }
    }

    @Override
    public int[] sqlTypes() {
        return sqlTypes;
    }

    @Override
    public Class<?> returnedClass() {
        return Date.class;
    };

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return (x == null) ? (y == null) : x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        String v = rs.getString(names[0]);
        v = v == null ? "" : v.trim();
        try {
            return "".equals(v) ? null : new SimpleDateFormat(format).parse(v);
        } catch (ParseException pe) {
            throw new HibernateException("could not parse date value: " + v, pe);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, sqlTypes[0]);
        } else {
            String v = new SimpleDateFormat(format).format(((Date) value));
            st.setString(index, v);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return (value == null) ? null : new Date(((Date) value).getTime());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}
