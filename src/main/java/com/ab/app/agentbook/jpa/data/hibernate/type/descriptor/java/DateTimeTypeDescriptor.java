package com.ab.app.agentbook.jpa.data.hibernate.type.descriptor.java;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeTypeDescriptor extends AbstractTypeDescriptor<DateTime> {

    private static final long serialVersionUID = -436050561977062381L;

    public static final DateTimeTypeDescriptor INSTANCE = new DateTimeTypeDescriptor();

    private DateTimeFormatter formatter;

    public DateTimeTypeDescriptor() {
        this(ISODateTimeFormat.dateTime());
    }

    public DateTimeTypeDescriptor(DateTimeFormatter formatter) {
        super(DateTime.class);
        this.formatter = formatter;
    }

    @Override
    public String toString(DateTime value) {
        return value == null ? "" : value.toString(formatter);
    }

    @Override
    public DateTime fromString(String string) {
        return formatter.parseDateTime(string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> X unwrap(DateTime value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (DateTime.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (Long.class.isAssignableFrom(type)) {
            Long v = Long.valueOf(value.getMillis());
            return (X) v;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) value.toString(formatter);
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> DateTime wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (DateTime.class.isInstance(value)) {
            return (DateTime) value;
        }

        if (Long.class.isInstance(value)) {
            return new DateTime((Long) value);
        }
        if (String.class.isInstance(value)) {
            String v = (String) value;
            return v.length() == 0 ? null : formatter.parseDateTime(v);
        }

        throw unknownWrap(value.getClass());
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
}
