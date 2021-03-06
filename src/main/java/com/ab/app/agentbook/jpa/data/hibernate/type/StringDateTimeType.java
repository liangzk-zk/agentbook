package com.ab.app.agentbook.jpa.data.hibernate.type;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.LiteralType;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 使用字符型数据来存储{@link DateTime}的hibernate类型，字符格式由
 * {@link ISODateTimeFormat#dateTime()}决定
 * 
 *
 */
public class StringDateTimeType extends AbstractSingleColumnStandardBasicType<DateTime>
        implements IdentifierType<DateTime>, LiteralType<DateTime> {
    private static final long serialVersionUID = 4854983651071735740L;

    public StringDateTimeType() {
        super(org.hibernate.type.descriptor.sql.VarcharTypeDescriptor.INSTANCE,
                com.ab.app.agentbook.jpa.data.hibernate.type.descriptor.java.DateTimeTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "string_datatime";
    }

    @Override
    public String objectToSQLString(DateTime value, Dialect dialect) throws Exception {
        return toString(value);
    }

    @Override
    public DateTime stringToObject(String xml) throws Exception {
        return fromString(xml);
    }
}
