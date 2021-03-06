package com.ab.app.agentbook.jpa.data.hibernate.type;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;

/**
 * Like a Hibernate time, but using the UTC TimeZone (not the default TimeZone).
 */
public class TimeType extends HibernateUTC {
    protected static int[] SQL_TYPES_TIME = { Types.TIME };
    public static final TimeType TYPE = new TimeType();
    public static final Type INSTANCE = new CustomType(TYPE);

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES_TIME;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return ((java.sql.Time) x).hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
        return rs.getTime(names[0], utcCalendar);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (!(value instanceof java.sql.Time)) {
            value = deepCopy(value);
        }
        Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
        st.setTime(index, (java.sql.Time) value, utcCalendar);
    }

    @Override
    public Object deepCopy(Object value) {
        return (value == null) ? null : new java.sql.Time(((Date) value).getTime());
    }

    @Override
    public Object seed(SharedSessionContractImplementor session) {
        return new java.sql.Time(System.currentTimeMillis());
    }

    @Override
    public Object next(Object current, SharedSessionContractImplementor session) {
        return new java.sql.Time(System.currentTimeMillis());
    }

    @Override
    public int compare(Object x, Object y) {
        if (x == null && y == null) {
            return 0;
        } else if (x == null) {
            return 1;
        } else if (y == null) {
            return -1;
        } else {
            java.sql.Time c1 = (java.sql.Time) x;
            java.sql.Time c2 = (java.sql.Time) y;
            return c1.compareTo(c2);
        }
    }
}
