package com.ab.app.agentbook.jpa.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.DefaultSerializer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.ab.app.agentbook.controller.criteron.Operator;
import com.ab.app.agentbook.data.crud.criteria.Comparison;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.data.crud.criteria.Group;
import com.ab.app.agentbook.data.crud.criteria.Junction;
import com.ab.app.agentbook.data.crud.criteria.OrderBy;


/**
 * 将{@link Expression}对象与{@link Criterion}对象相互转换的工具类
 */
public abstract class ExpressionUtils {

    private static Serializer<Object> defaultValueSerializer = new DefaultSerializer();
    private static Deserializer<Object> defaultValueDeserializer = new DefaultDeserializer();

    /**
     * 返回与约束表达式集合相对应的Criterion对象集合
     * 
     * @param expressions 约束表达式集合
     * @return
     * @see #toExpression(Criterion[])
     */
    public static Criterion[] getCriteria(Expression[] expressions) {
        return getCriteria(expressions, null);
    }

    /**
     * 返回与约束表达式集合相对应的Criterion对象集合
     * 
     * @param expressions  约束表达式集合
     * @param fieldMapping 字段映射对应表，key为Expression对象中的字段名，value为对应的criterion对象中的字段名
     * @return
     * @see #toExpression(Criterion, Map)
     */
    public static Criterion[] getCriteria(Expression[] expressions, Map<String, String> fieldMapping) {
        Criterion[] criterions;
        if (expressions != null) {
            criterions = new Criterion[expressions.length];
            for (int i = 0; i < expressions.length; i++) {
                criterions[i] = getCriterion(expressions[i], fieldMapping);
            }
        } else {
            criterions = new Criterion[0];
        }
        return criterions;
    }

    /**
     * 返回与约束对应的Criterion对象
     * 
     * @param expression
     * @return
     * @see #toExpression(Criterion)
     */
    public static Criterion getCriterion(Expression expression) {
        return getCriterion(expression, null);
    }

    /**
     * 返回与指定的约束对应的Criterion对象
     * 
     * @param expression   约束表达式
     * @param fieldMapping 字段映射对应表，key为Expression对象中的字段名，value为对应的criterion对象中的字段名
     * @return
     * @see #toExpression(Criterion, Map)
     */
    public static Criterion getCriterion(Expression expression, Map<String, String> fieldMapping) {
        String name = expression.getName();
        if (fieldMapping != null && fieldMapping.containsKey(name)) {
            name = fieldMapping.get(name);
        }
        String op = expression.getOp();
        Object value = null;
        String type = expression.getDataType();
        if (Expression.DATATYPE.BOOLEAN.equals(type)) {
            value = expression.getBooleanValue();
        } else if (Expression.DATATYPE.SHORT.equals(type)) {
            value = expression.getShortValue();
        } else if (Expression.DATATYPE.INT.equals(type)) {
            value = expression.getIntValue();
        } else if (Expression.DATATYPE.LONG.equals(type)) {
            value = expression.getLongValue();
        } else if (Expression.DATATYPE.FLOAT.equals(type)) {
            value = expression.getLongValue();
        } else if (Expression.DATATYPE.DOUBLE.equals(type)) {
            value = expression.getLongValue();
        } else if (Expression.DATATYPE.DATE.equals(type)) {
            value = expression.getDateValue();
        } else if (Expression.DATATYPE.DATETIME.equals(type)) {
            value = expression.getDateTimeValue();
        } else if (Expression.DATATYPE.STRING.equals(type)) {
            value = expression.getStringValue();
        } else if (Expression.DATATYPE.OBJECT.equals(type)) {
            value = fromByteArray(expression.getObjectDataValue());
        } else if (Expression.DATATYPE.EXPRESSION.equals(type)) {
            value = expression.getExpressionValue();
        } else if (Expression.DATATYPE.EXPRESSIONS.equals(type)) {
            value = expression.getExpValue();
        } else {
            throw new IllegalArgumentException("unsupported data type " + type);
        }
        if (Operator.EQ.equals(op)) {
            return Comparison.eq(name, value);
        } else if (Operator.NE.equals(op)) {
            return Comparison.ne(name, value);
        } else if (Operator.LE.equals(op)) {
            return Comparison.le(name, value);
        } else if (Operator.GE.equals(op)) {
            return Comparison.ge(name, value);
        } else if (Operator.GT.equals(op)) {
            return Comparison.gt(name, value);
        } else if (Operator.LT.equals(op)) {
            return Comparison.lt(name, value);
        } else if (Operator.BETWEEN.equals(op)) {
            Object[] v = (Object[]) value;
            return Comparison.between(name, v[0], v[1]);
        } else if (Operator.IN.equals(op)) {
            return Comparison.in(name, value);
        } else if (Operator.LIKE.equals(op)) {
            return Comparison.like(name, (String) value);
        } else if (Operator.IS_NULL.equals(op)) {
            return Comparison.isNull(name);
        } else if (Operator.IS_NOT_NULL.equals(op)) {
            return Comparison.isNotNull(name);
        } else if (Operator.AND.equals(op)) {
            Expression[] exps = (Expression[]) value;
            Criterion[] criteria = new Criterion[0];
            if (exps != null) {
                criteria = new Criterion[exps.length];
                for (int i = 0; i < exps.length; i++) {
                    criteria[i] = getCriterion(exps[i], fieldMapping);
                }
            }
            return Group.and(criteria);
        } else if (Operator.OR.equals(op)) {
            Expression[] exps = (Expression[]) value;
            Criterion[] criteria = new Criterion[0];
            if (exps != null) {
                criteria = new Criterion[exps.length];
                for (int i = 0; i < exps.length; i++) {
                    criteria[i] = getCriterion(exps[i], fieldMapping);
                }
            }
            return Group.or(criteria);
        } else {
            throw new IllegalArgumentException("unknown expression type " + op);
        }
    }

    /**
     * 将查询的排序设置信息转换为对应的OrderBy对象集合
     * 
     * @param orderBy 排序设置信息，格式为“字段1 排序方式,字段2 排序方式...”，排序方式是ASC/或DESC
     * @return
     * @see #toString(OrderBy)
     */
    public static OrderBy[] getOrderBy(String orderBy) {
        return getOrderBy(orderBy, null);
    }

    /**
     * 将查询的排序设置信息转换为对应的OrderBy对象集合
     * 
     * @param orderBy      排序设置信息，格式为“字段1 排序方式,字段2 排序方式...”，排序方式是ASC/或DESC
     * @param fieldMapping 字段映射对应表，key为原始表达式中的字段名，value为对应的OrderBy对象中的字段名
     * @return
     * 
     */
    public static OrderBy[] getOrderBy(String orderBy, Map<String, String> fieldMapping) {
        String[] orders = StringUtils.split(orderBy, ',');
        if (orders == null || orders.length == 0) {
            return new OrderBy[0];
        }
        OrderBy[] result = new OrderBy[orders.length];
        for (int i = 0; i < orders.length; i++) {
            String order = StringUtils.trim(orders[i]);
            if (StringUtils.isEmpty(order)) {
                throw new IllegalArgumentException("orderBy is null!");
            }
            int pos = order.indexOf(' ');
            String field = pos == -1 ? order : order.substring(0, pos);
            if (fieldMapping != null && fieldMapping.containsKey(field)) {
                field = fieldMapping.get(field);
            }
            String dir = pos == -1 ? "ASC" : StringUtils.trim(order.substring(pos + 1));
            result[i] = "ASC".equalsIgnoreCase(dir) ? OrderBy.asc(field) : OrderBy.desc(field);
        }
        return result;
    }

    /**
     * 返回与Criterion对象集合相对应的约束表达式集合
     * 
     * @param criteria
     * @return
     * @see #getCriteria(Expression[])
     */
    public static Expression[] toExpression(Criterion[] criteria) {
        return toExpression(criteria, null);
    }

    /**
     * 返回与Criterion对象集合相对应的约束表达式集合
     * 
     * @param criteria
     * @param fieldMapping 字段映射对应表，key为Criterion对象中的字段名，value为对应的Expression对象中的字段名
     * @return
     * @see #getCriteria(Expression[], Map)
     */
    public static Expression[] toExpression(Criterion[] criteria, Map<String, String> fieldMapping) {
        if (criteria == null) {
            return new Expression[0];
        } else {
            Expression[] exps = new Expression[criteria.length];
            for (int i = 0; i < criteria.length; i++) {
                exps[i] = toExpression(criteria[i], fieldMapping);
            }
            return exps;
        }
    }

    /**
     * 返回与Criterion对象相对应的约束表达式
     * 
     * @param criterion
     * @return
     * @see #getCriterion(Expression)
     */
    public static Expression toExpression(Criterion criterion) {
        return toExpression(criterion, null);
    }

    /**
     * 返回与Criterion对象相对应的约束表达式
     * 
     * @param criterion
     * @param fieldMapping 字段映射对应表，key为Criterion对象中的字段名，value为对应的Expression对象中的字段名
     * @return
     * @see #getCriterion(Expression, Map)
     */
    public static Expression toExpression(Criterion criterion, Map<String, String> fieldMapping) {
        if (criterion instanceof Comparison) {
            Comparison cp = (Comparison) criterion;
            String name = cp.getName();
            if (fieldMapping != null && fieldMapping.containsKey(name)) {
                name = fieldMapping.get(name);
            }

            Expression exp = new Expression();
            exp.setName(name);

            com.ab.app.agentbook.data.crud.criteria.Operator op = cp.getOperator();
            switch (op) {
            case EQ:
                exp.setOp(Operator.EQ);
                setExpressionValue(exp, cp.getValue());
                break;
            case NE:
                exp.setOp(Operator.NE);
                setExpressionValue(exp, cp.getValue());
                break;
            case LE:
                exp.setOp(Operator.LE);
                setExpressionValue(exp, cp.getValue());
                break;
            case GE:
                exp.setOp(Operator.GE);
                setExpressionValue(exp, cp.getValue());
                break;
            case GT:
                exp.setOp(Operator.GT);
                setExpressionValue(exp, cp.getValue());
                break;
            case LT:
                exp.setOp(Operator.LT);
                setExpressionValue(exp, cp.getValue());
                break;
            case BETWEEN:
                exp.setOp(Operator.BETWEEN);
                setExpressionValue(exp, cp.getValue());
                break;
            case IN:
                exp.setOp(Operator.IN);
                throw new UnsupportedOperationException("unsupported comparison: in");
            case LIKE:
                exp.setOp(Operator.LIKE);
                setExpressionValue(exp, cp.getValue());
                break;
            case IS_NULL:
                exp.setOp(Operator.IS_NULL);
                break;
            case IS_NOT_NULL:
                exp.setOp(Operator.IS_NOT_NULL);
                break;
            case NOT:
                exp.setOp(Operator.NOT);
                exp.setExpressionValue(toExpression((Criterion) cp.getValue(), fieldMapping));
                exp.setDataType(Expression.DATATYPE.EXPRESSION);
                break;
            default:
                throw new IllegalArgumentException("unsupport comparison: " + op);
            }
            return exp;
        } else if (criterion instanceof Group) {
            Expression exp = new Expression();
            exp.setOp(((Group) criterion).getJunction() == Junction.AND ? Operator.AND : Operator.OR);
            exp.setDataType(Expression.DATATYPE.EXPRESSIONS);
            List<Criterion> list = ((Group) criterion).getCriteria();
            List<Expression> exps = new ArrayList<Expression>(list.size());
            for (Criterion c : list) {
                exps.add(toExpression(c, fieldMapping));
            }
            exp.setExpValue(exps.toArray(new Expression[exps.size()]));
            return exp;
        } else {
            throw new IllegalArgumentException("unsupport criterion: " + criterion.getClass());
        }
    }

    protected static void setExpressionValue(Expression exp, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof Boolean) {
            exp.setBooleanValue((Boolean) value);
            exp.setDataType(Expression.DATATYPE.BOOLEAN);
        } else if (value instanceof Short) {
            exp.setShortValue((Short) value);
            exp.setDataType(Expression.DATATYPE.SHORT);
        } else if (value instanceof Integer) {
            exp.setIntValue((Integer) value);
            exp.setDataType(Expression.DATATYPE.INT);
        } else if (value instanceof Long) {
            exp.setLongValue((Long) value);
            exp.setDataType(Expression.DATATYPE.LONG);
        } else if (value instanceof Float) {
            exp.setFloatValue((Float) value);
            exp.setDataType(Expression.DATATYPE.FLOAT);
        } else if (value instanceof Double) {
            exp.setDoubleValue((Double) value);
            exp.setDataType(Expression.DATATYPE.DOUBLE);
        } else if (value instanceof Date) {
            exp.setDateValue((Date) value);
            exp.setDataType(Expression.DATATYPE.DATE);
        } else if (value instanceof DateTime) {
            exp.setDateTimeValue((DateTime) value);
            exp.setDataType(Expression.DATATYPE.DATETIME);
        } else if (value instanceof String) {
            exp.setStringValue((String) value);
            exp.setDataType(Expression.DATATYPE.STRING);
        } else {
            exp.setObjectDataValue(toByteArray(value));
            exp.setDataType(Expression.DATATYPE.OBJECT);
        }
    }

    /**
     * 返回与OrderBy对象集合对应的字符串
     * 
     * @param orders
     * @return
     * @see #getOrderBy(String)
     */
    public static String toString(OrderBy[] orders) {
        return toString(orders, null);
    }

    /**
     * 返回与OrderBy对象集合对应的字符串
     * 
     * @param orders
     * @param fieldMapping 字段映射对应表，key为OrderBy对象中的字段名，value为对应的Expression对象中的字段名
     * @return
     * @see #getOrderBy(String, Map)
     */
    public static String toString(OrderBy[] orders, Map<String, String> fieldMapping) {
        if (orders == null || orders.length == 0) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < orders.length; i++) {
            if (i > 0) {
                b.append(',');
            }
            b.append(toString(orders[i], fieldMapping));
        }
        return b.toString();
    }

    /**
     * 返回与OrderBy对象对应的字符串
     * 
     * @param order
     * @return
     * @see #getOrderBy(String)
     */
    public static String toString(OrderBy order) {
        return toString(order, null);
    }

    /**
     * 返回与OrderBy对象对应的字符串
     * 
     * @param order
     * @param fieldMapping 字段映射对应表，key为OrderBy对象中的字段名，value为对应的Expression对象中的字段名
     * @return
     * @see #getOrderBy(String, Map)
     */
    public static String toString(OrderBy order, Map<String, String> fieldMapping) {
        String name = order.getName();
        if (fieldMapping != null && fieldMapping.containsKey(name)) {
            name = fieldMapping.get(name);
        }
        StringBuilder b = new StringBuilder();
        b.append(name).append(' ').append(order.isAsc() ? "asc" : "desc");
        return b.toString();
    }

    /**
     * 返回与值对象对应的字节数组
     * 
     * @param value
     * @return
     * @throws IOException
     * @throws IllegalArgumentException 值对象无法序列化为字节数组时抛出
     */
    public static byte[] toByteArray(Object value) {
        return toByteArray(value, null);
    }

    /**
     * 返回与值对象对应的字节数组
     * 
     * @param value
     * @param serializer
     * @return
     * @throws IllegalArgumentException 值对象无法序列化为字节数组时抛出
     * @see #fromByteArray(byte[])
     */
    public static <T> byte[] toByteArray(T value, Serializer<T> serializer) {
        if (value == null) {
            return null;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            if (serializer == null) {
                defaultValueSerializer.serialize(value, out);
            } else {
                serializer.serialize(value, out);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("serialize value failure!", e);
        }
    }

    /**
     * 返回与字节数据对象相对应的值对象
     * 
     * @param data
     * @return
     * @throws IllegalArgumentException 数据无法还原为对象时抛出
     * @see #toByteArray(Object)
     */
    public static Object fromByteArray(byte[] data) {
        return fromByteArray(data, null);
    }

    /**
     * 返回与字节数据对象相对应的值对象
     * 
     * @param data
     * @param deserializer
     * @return
     * @throws IllegalArgumentException 数据无法还原为对象时抛出
     * @see #toByteArray(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromByteArray(byte[] data, Deserializer<T> deserializer) {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            return deserializer == null ? (T) defaultValueDeserializer.deserialize(new ByteArrayInputStream(data))
                    : deserializer.deserialize(new ByteArrayInputStream(data));
        } catch (IOException e) {
            throw new IllegalArgumentException("deserialize value failure", e);
        }
    }
}
