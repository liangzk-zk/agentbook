package com.ab.app.agentbook.jpa.ws;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.core.serializer.Serializer;

/**
 * 适用于web服务中的查询条件定义
 *
 */
public class Expression implements Serializable {
    private static final long serialVersionUID = -4469978314629325835L;

    /**
     * 查询条件值所支持的数据类型
     */
    public static class DATATYPE {
        /**
         * 布尔类型
         */
        public static String BOOLEAN = "boolean";
        /**
         * 短整型
         */
        public static String SHORT = "short";
        /**
         * 整型
         */
        public static String INT = "int";
        /**
         * 长整型
         */
        public static String LONG = "long";
        /**
         * 浮点型
         */
        public static String FLOAT = "float";
        /**
         * 双精度型
         */
        public static String DOUBLE = "double";
        /**
         * java.util.Date型
         */
        public static String DATE = "date";
        /**
         * joda DateTime型
         */
        public static String DATETIME = "dateTime";
        /**
         * 字符型
         */
        public static String STRING = "str";
        /**
         * 对象型
         */
        public static String OBJECT = "obj";
        /**
         * 查询条件
         */
        public static String EXPRESSION = "exp";
        /**
         * 查询条件集合
         */
        public static String EXPRESSIONS = "exps";
    }

    /**
     * 字段名称
     */
    private String name;
    /**
     * 条件类型
     */
    private String op;
    private String dataType;
    private boolean booleanValue;
    private short shortValue;
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private Date dateValue;
    private DateTime dateTimeValue;
    private String stringValue;
    private byte[] objValue;

    private Expression expressionValue;
    private Expression[] expValue;

    /**
     * 空白构造函数
     */
    public Expression() {
        super();
    }

    public Expression(String name, String op, boolean booleanValue) {
        super();
        this.name = name;
        this.op = op;
        this.booleanValue = booleanValue;
        this.dataType = DATATYPE.BOOLEAN;
    }

    public Expression(String name, String op, short shortValue) {
        super();
        this.name = name;
        this.op = op;
        this.shortValue = shortValue;
        this.dataType = DATATYPE.SHORT;
    }

    public Expression(String name, String op, int intValue) {
        super();
        this.name = name;
        this.op = op;
        this.intValue = intValue;
        this.dataType = DATATYPE.INT;
    }

    public Expression(String name, String op, long longValue) {
        super();
        this.name = name;
        this.op = op;
        this.longValue = longValue;
        this.dataType = DATATYPE.LONG;
    }

    public Expression(String name, String op, float floatValue) {
        super();
        this.name = name;
        this.op = op;
        this.floatValue = floatValue;
        this.dataType = DATATYPE.FLOAT;
    }

    public Expression(String name, String op, double doubleValue) {
        super();
        this.name = name;
        this.op = op;
        this.doubleValue = doubleValue;
        this.dataType = DATATYPE.DOUBLE;
    }

    public Expression(String name, String op, Date dateValue) {
        super();
        this.name = name;
        this.op = op;
        this.dateValue = dateValue;
        this.dataType = DATATYPE.DATE;
    }

    public Expression(String name, String op, DateTime dateTimeValue) {
        super();
        this.name = name;
        this.op = op;
        this.dateTimeValue = dateTimeValue;
        this.dataType = DATATYPE.DATETIME;
    }

    public Expression(String name, String op, String strValue) {
        super();
        this.name = name;
        this.op = op;
        this.stringValue = strValue;
        this.dataType = DATATYPE.STRING;
    }

    public Expression(String name, String op, Object objValue) {
        super();
        this.name = name;
        this.op = op;
        this.objValue = ExpressionUtils.toByteArray(objValue);
        this.dataType = DATATYPE.OBJECT;
    }

    public <T> Expression(String name, String op, T objValue, Serializer<T> serializer) {
        super();
        this.name = name;
        this.op = op;
        this.objValue = ExpressionUtils.toByteArray(objValue, serializer);
        this.dataType = DATATYPE.OBJECT;
    }

    public Expression(String name, String op, Expression expression) {
        super();
        this.name = name;
        this.op = op;
        this.expressionValue = expression;
        this.dataType = DATATYPE.EXPRESSION;
    }

    public Expression(String name, String op, Expression[] expressions) {
        super();
        this.name = name;
        this.op = op;
        this.expValue = expressions;
        this.dataType = DATATYPE.EXPRESSIONS;
    }

    /**
     * 返回查询的属性名字
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置查询的属性名字
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回查询条件类型，类型必须为{@link com.hd.rcugrc.web.dwr.criterion.Operator}所定义的类型之一
     * 
     * @return
     * @see com.hd.rcugrc.web.dwr.criterion.Operator
     */
    public String getOp() {
        return op;
    }

    /**
     * 设置查询条件类型，类型必须为{@link com.hd.rcugrc.web.dwr.criterion.Operator}所定义的类型之一
     * 
     * @param op
     * @see com.hd.rcugrc.web.dwr.criterion.Operator
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * 返回条件数据所使用的数据类型，必须为{@link DATETYPE}所定义的类型之一
     * 
     * @return
     * @see DATATYPE
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * 设置条件数据所使用的数据类型，必须为{@link DATETYPE}所定义的类型之一
     * 
     * @param dataType
     * @see DATATYPE
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 返回布尔值形式的查询条件值
     * 
     * @return
     */
    public boolean getBooleanValue() {
        return booleanValue;
    }

    /**
     * 设置布尔值形式的查询条件值
     * 
     * @param value
     */
    public void setBooleanValue(boolean value) {
        this.booleanValue = value;
    }

    /**
     * 返回短整型形式的查询条件值
     * 
     * @return
     */
    public short getShortValue() {
        return shortValue;
    }

    /**
     * 设置短整型形式的查询条件值
     * 
     * @param value
     */
    public void setShortValue(short value) {
        this.shortValue = value;
    }

    /**
     * 返回整数形式的查询条件值
     * 
     * @return
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * 设置整数形式的查询条件值
     * 
     * @param value
     */
    public void setIntValue(int value) {
        this.intValue = value;
    }

    /**
     * 返回长整数形式的查询条件值
     * 
     * @return
     */
    public long getLongValue() {
        return longValue;
    }

    /**
     * 设置长整数形式的查询条件值
     * 
     * @param value
     */
    public void setLongValue(long value) {
        this.longValue = value;
    }

    /**
     * 返回浮点数形式的查询条件值
     * 
     * @return
     */
    public float getFloatValue() {
        return floatValue;
    }

    /**
     * 设置浮点数形式的查询条件值
     * 
     * @param value
     */
    public void setFloatValue(float value) {
        this.floatValue = value;
    }

    /**
     * 返回双精度数形式的查询条件值
     * 
     * @return
     */
    public double getDoubleValue() {
        return doubleValue;
    }

    /**
     * 设置双精度数形式的查询条件值
     * 
     * @param value
     */
    public void setDoubleValue(double value) {
        this.doubleValue = value;
    }

    /**
     * 返回日期值形式的查询条件值
     * 
     * @return
     */
    public Date getDateValue() {
        return dateValue;
    }

    /**
     * 设置日期值形式的查询条件值
     * 
     * @param value
     */
    public void setDateValue(Date value) {
        this.dateValue = value;
    }

    /**
     * 返回{@link DateTime}形式的查询条件值
     * 
     * @return
     */
    public DateTime getDateTimeValue() {
        return dateTimeValue;
    }

    /**
     * 设置{@link DateTime}形式的查询条件值
     * 
     * @param value
     */
    public void setDateTimeValue(DateTime value) {
        this.dateTimeValue = value;
    }

    /**
     * 返回字符形式的查询条件值
     * 
     * @return
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * 返回查询条件对象值序列化后的字符数组
     * 
     * @return
     */
    public byte[] getObjectDataValue() {
        return objValue;
    }

    /**
     * 设置查询条件对象值序列化后的字符数组
     * 
     * @param data
     */
    public void setObjectDataValue(byte[] data) {
        this.objValue = data;
    }

    /**
     * 设置字符形式的查询条件值
     * 
     * @param value
     */
    public void setStringValue(String value) {
        this.stringValue = value;
    }

    /**
     * 返回查询条件类型的查询条件值，用于构造not表达式
     * 
     * @param value
     */
    public Expression getExpressionValue() {
        return expressionValue;
    }

    /**
     * 设置查询条件类型的查询条件值，用于构造not表达式
     * 
     * @param value
     */
    public void setExpressionValue(Expression value) {
        this.expressionValue = value;
    }
    /**
     * 返回条件集合形式的查询条件值
     * 
     * @return
     */
	public Expression[] getExpValue() {
		return expValue;
	}

    /**
     * 设置条件集合形式的查询条件值
     * 
     * @param value
     */
	public void setExpValue(Expression[] expValue) {
		this.expValue = expValue;
	}

}
