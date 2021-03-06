package com.ab.app.agentbook.jpa.data.hibernate.type;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.LiteralType;
import org.joda.time.DateTime;

/**
 * 使用长整型数据来存储{@link DateTime}的hibernate类型
 * 
 *
 */
public class LongDateTimeType extends AbstractSingleColumnStandardBasicType<DateTime>
        implements IdentifierType<DateTime>, LiteralType<DateTime> {
    private static final long serialVersionUID = -5948961182168244859L;

    public LongDateTimeType() {
        super(org.hibernate.type.descriptor.sql.BigIntTypeDescriptor.INSTANCE,
                com.ab.app.agentbook.jpa.data.hibernate.type.descriptor.java.DateTimeTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "long_datatime";
    }

    @Override
    public String objectToSQLString(DateTime value, Dialect dialect) throws Exception {
        return Long.toString(value.getMillis());
    }

    @Override
    public DateTime stringToObject(String xml) throws Exception {
        long l = Long.parseLong(xml);
        return new DateTime(l);
    }
}
