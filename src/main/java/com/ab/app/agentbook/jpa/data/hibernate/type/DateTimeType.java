package com.ab.app.agentbook.jpa.data.hibernate.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * 使用UTC保存{@link DateTime}的hibernate类型
 */
public class DateTimeType extends HibernateUTC {
    public static final DateTimeType TYPE = new DateTimeType();

    public static final Type INSTANCE = new CustomType(TYPE);

    @Override
    public Class<?> returnedClass() {
        return DateTime.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
        Timestamp t = rs.getTimestamp(names[0], utcCalendar);
        return t == null ? null
                : new DateTime(t.getTime(), DateTimeZone.UTC).withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.TIMESTAMP);
        } else {
            Timestamp t = new Timestamp(((DateTime) value).withZone(DateTimeZone.UTC).getMillis());
            Calendar utcCalendar = (Calendar) UTC_CALENDAR.clone();
            st.setTimestamp(index, t, utcCalendar);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
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
            DateTime d1 = (DateTime) x;
            DateTime d2 = (DateTime) y;
            return compare(d1, d2);
        }
    }

    @Override
    public Object seed(SharedSessionContractImplementor session) {
        return DateTime.now();
    }

    @Override
    public Object next(Object current, SharedSessionContractImplementor session) {
        return DateTime.now();
    }

}
