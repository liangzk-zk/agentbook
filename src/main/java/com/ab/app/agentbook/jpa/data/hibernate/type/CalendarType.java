package com.ab.app.agentbook.jpa.data.hibernate.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;

public class CalendarType extends HibernateUTC {
    public static final CalendarType TYPE = new CalendarType();
    public static final Type INSTANCE = new CustomType(TYPE);

    @Override
    public Class<?> returnedClass() {
        return Calendar.class;
    }

    @Override
    public boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }

        Calendar calendar1 = (Calendar) x;
        Calendar calendar2 = (Calendar) y;

        return calendar1.getTimeInMillis() == calendar2.getTimeInMillis();
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return ((Calendar) x).hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        Calendar cal = (Calendar) UTC_CALENDAR.clone();
        Timestamp ts = rs.getTimestamp(names[0], cal);
        if (ts == null || rs.wasNull()) {
            return null;
        }
        cal.setTime(ts);
        return cal;

    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.TIMESTAMP);
        } else {
            Timestamp t = new Timestamp(((Calendar) value).getTimeInMillis());
            Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
            st.setTimestamp(index, t, utcCalendar);
        }
    }

    @Override
    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        Calendar c = (Calendar) UTC_CALENDAR.clone();
        c.setTimeInMillis(((Calendar) value).getTimeInMillis());
        return c;
    }

    @Override
    public Object seed(SharedSessionContractImplementor session) {
        Calendar cal = (Calendar) UTC_CALENDAR.clone();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal;
    }

    @Override
    public Object next(Object current, SharedSessionContractImplementor session) {
        Calendar cal = (Calendar) UTC_CALENDAR.clone();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal;
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
            Calendar c1 = (Calendar) x;
            Calendar c2 = (Calendar) y;
            return compare(c1, c2);
        }
    }
}
