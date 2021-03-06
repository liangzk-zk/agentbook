package com.ab.app.agentbook.jpa.data.hibernate.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;

/**
 * Like a Hibernate timestamp, but using the UTC TimeZone (not the default
 * TimeZone).
 */
public class TimestampType extends HibernateUTC {
    public static final TimestampType TYPE = new TimestampType();
    public static final Type INSTANCE = new CustomType(TYPE);

    @Override
    public Class<?> returnedClass() {
        return Timestamp.class;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return ((Timestamp) x).hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
        return rs.getTimestamp(names[0], utcCalendar);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (!(value instanceof java.sql.Timestamp)) {
            value = deepCopy(value);
        }
        Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
        st.setTimestamp(index, (java.sql.Timestamp) value, utcCalendar);
    }

    @Override
    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        java.sql.Timestamp ots = (java.sql.Timestamp) value;
        java.sql.Timestamp ts = new java.sql.Timestamp(ots.getTime());
        ts.setNanos(ots.getNanos());
        return ts;
    }

    @Override
    public Object seed(SharedSessionContractImplementor session) {
        return new Timestamp(System.currentTimeMillis());
        // return new Timestamp(si.getTimestamp());
    }

    @Override
    public Object next(Object current, SharedSessionContractImplementor session) {
        return new Timestamp(System.currentTimeMillis());
        // return new Timestamp(si.getTimestamp());
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
            Timestamp c1 = (Timestamp) x;
            Timestamp c2 = (Timestamp) y;
            return c1.compareTo(c2);
        }
    }
}
