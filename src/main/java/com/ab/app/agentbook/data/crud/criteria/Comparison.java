package com.ab.app.agentbook.data.crud.criteria;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 字段值比较方式的查询约束条件
 * 
 *
 */
public class Comparison extends Criterion {
    private static final long serialVersionUID = 3290751641707690169L;

    private String name;
    private Operator operator;
    private Object value;
    private boolean alias = false;
    private boolean enabled = false;
    private boolean objectIdentity;
    private boolean caseSensitive = true;
    private transient Integer hashCode = null;

    /**
     * 构造字段值位于指定对象之间的查询条件
     * 
     * @param name   字段名字
     * @param value1 数值1
     * @param value2 数值2
     * @return
     */
    public static Comparison between(final String name, final Object value1, final Object value2) {
        return new Between(name, value1, value2);
    }

    /**
     * 构造字段值位于指定对象之间的查询条件
     * 
     * @param name   字段名字
     * @param alias  字段名字是否是别名
     * @param value1 数值1
     * @param value2 数值2
     * @return
     */
    public static Comparison between(final String name, final boolean alias, final Object value1, final Object value2) {
        return new Between(name, value1, value2, alias);
    }

    /**
     * 构造字段值是指定数值之一的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison in(final String name, final Object value) {
        return new Comparison(name, Operator.IN, value);
    }

    /**
     * 构造字段值是指定数值之一的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison in(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.IN, value, alias);
    }

    /**
     * 构造字段值是指定数值之一的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison in(final String name, final Object... value) {
        return new Comparison(name, Operator.IN, Arrays.asList(value));
    }

    /**
     * 构造字段值是指定数值之一的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison in(final String name, final boolean alias, final Object... value) {
        return new Comparison(name, Operator.IN, Arrays.asList(value), alias);
    }

    /**
     * 构造字段值等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison eq(final String name, final Object value) {
        return new Comparison(name, Operator.EQ, value);
    }

    /**
     * 构造字段值等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison eq(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.EQ, value, alias);
    }

    /**
     * 构造字段值不等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison ne(final String name, final Object value) {
        return new Comparison(name, Operator.NE, value);
    }

    /**
     * 构造字段值不等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison ne(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.NE, value, alias);
    }

    /**
     * 构造字段值大于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison gt(final String name, final Object value) {
        return new Comparison(name, Operator.GT, value);
    }

    /**
     * 构造字段值大于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison gt(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.GT, value, alias);
    }

    /**
     * 构造字段值小于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison lt(final String name, final Object value) {
        return new Comparison(name, Operator.LT, value);
    }

    /**
     * 构造字段值小于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison lt(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.LT, value, alias);
    }

    /**
     * 构造字段值大于或等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison ge(final String name, final Object value) {
        return new Comparison(name, Operator.GE, value);
    }

    /**
     * 构造字段值大于或等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison ge(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.GE, value, alias);
    }

    /**
     * 构造字段值小于或等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison le(final String name, final Object value) {
        return new Comparison(name, Operator.LE, value);
    }

    /**
     * 构造字段值小于或等于指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison le(final String name, final boolean alias, final Object value) {
        return new Comparison(name, Operator.LE, value, alias);
    }

    /**
     * 构造字段值类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison like(final String name, final String value) {
        return new Comparison(name, Operator.LIKE, value);
    }

    /**
     * 构造字段值类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison like(final String name, final boolean alias, final String value) {
        return new Comparison(name, Operator.LIKE, value, alias);
    }

    /**
     * 构造字段值类似指定数值(忽略大小写)的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison ilike(String name, String value) {
        return new Comparison(name, Operator.ILIKE, value);
    }

    /**
     * 构造字段值类似指定数值(忽略大小写)的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison ilike(String name, boolean alias, String value) {
        return new Comparison(name, Operator.ILIKE, value, alias);
    }

    /**
     * 构造字段值开始部分类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison startsLike(final String name, final String value) {
        return new Comparison(name, Operator.LIKE_START, value);
    }

    /**
     * 构造字段值开始部分类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison startsLike(final String name, final boolean alias, final String value) {
        return new Comparison(name, Operator.LIKE_START, value, alias);
    }

    /**
     * 构造字段值结尾部分类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison endsLike(final String name, final String value) {
        return new Comparison(name, Operator.LIKE_END, value);
    }

    /**
     * 构造字段值为{@code null}的查询条件
     * 
     * @param name 字段名字
     * @return
     */
    public static Comparison isNull(final String name) {
        return new Comparison(name, Operator.IS_NULL, null);
    }

    /**
     * 构造字段值不为{@code null}的查询条件
     * 
     * @param name 字段名字
     * @return
     */
    public static Comparison isNotNull(final String name) {
        return new Comparison(name, Operator.IS_NOT_NULL, null);
    }

    /**
     * 构造字段值结尾部分类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison endsLike(final String name, final boolean alias, final String value) {
        return new Comparison(name, Operator.LIKE_END, value, alias);
    }

    /**
     * 构造字段值包含类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param value 字段值
     * @return
     */
    public static Comparison containsLike(final String name, final String value) {
        return new Comparison(name, Operator.LIKE_CONTAINS, value);
    }

    /**
     * 构造字段值包含类似指定数值的查询条件
     * 
     * @param name  字段名字
     * @param alias 字段名字是否是别名
     * @param value 字段值
     * @return
     */
    public static Comparison containsLike(final String name, final boolean alias, final String value) {
        return new Comparison(name, Operator.LIKE_CONTAINS, value, alias);
    }

    /**
     * 构造在现有查询条件前添加{@code NOT}前缀的查询条件
     * 
     * @param criterion 待添加{@code NOT}前缀的查询条件
     * @return
     */
    public static Comparison not(Criterion criterion) {
        return new Comparison(null, Operator.NOT, criterion);
    }

    /**
     * 空构造函数
     */
    public Comparison() {
        super();
    }

    public Comparison(final String aName, final Operator aOperator, final Object aValue) {
        this.name = aName;
        // 原作者为何将_替换为.
        // if (this.name.contains( "_" )) {
        // this.name = name.replace( '_', '.');
        // }
        this.operator = aOperator;
        this.value = aValue;
    }

    /**
     * 构造函数
     * 
     * @param aName     条件所涉及的字段
     * @param aOperator 条件操作符
     * @param aValue    条件值
     * @param alias     字段名字是否是别名
     */
    public Comparison(String aName, Operator aOperator, Object aValue, boolean alias) {
        this(aName, aOperator, aValue);
        this.alias = alias;
    }

    /**
     * 返回条件所涉及的字段
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置条件所涉及的字段
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        propertyValueChanged();
    }

    /**
     * 返回条件操作符
     * 
     * @return
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * 设置条件操作符
     * 
     * @param operator
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
        propertyValueChanged();
    }

    /**
     * 返回条件值
     * 
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置条件值
     * 
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
        propertyValueChanged();
    }

    /**
     * 返回字段名称是否是别名
     * 
     * @return
     */
    public boolean isAlias() {
        return this.alias;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        propertyValueChanged();
    }

    public void enable() {
        this.enabled = true;
        propertyValueChanged();
    }

    public void disable() {
        this.enabled = false;
        propertyValueChanged();
    }

    public static Criterion objectEq(String o1, String o2) {
        Comparison comp = new Comparison();
        comp.setEnabled(true);
        comp.setName(o1);
        comp.setValue(o2);
        comp.setObjectIdentity(true);
        return comp;
    }

    public boolean isObjectIdentity() {
        return objectIdentity;
    }

    public void setObjectIdentity(boolean objectIdentity) {
        this.objectIdentity = objectIdentity;
        propertyValueChanged();
    }

    /**
     * 返回数值比较时是否大小写敏感，缺省为{@code true}
     * 
     * @return
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * 设置数值比较时是否大小写敏感，缺省为{@code true}
     * 
     * @param caseSensitive
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        propertyValueChanged();
    }

    @Override
    public String toString() {
        return name + "_" + operator + "_" + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Comparison)) {
            return false;
        }
        Comparison c = (Comparison) obj;
        if (this.hashCode != null && c.hashCode != null && !this.hashCode.equals(c.hashCode)) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.operator, c.operator);
        builder.append(this.name, c.name);
        builder.append(this.value, c.value);
        builder.append(this.alias, c.alias);
        builder.append(this.enabled, c.enabled);
        builder.append(this.objectIdentity, c.objectIdentity);
        builder.append(this.caseSensitive, c.caseSensitive);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        if (this.hashCode == null) {
            HashCodeBuilder builder = new HashCodeBuilder();
            builder.append(operator == null ? "[]" : operator.name());
            builder.append(name);
            builder.append(value);
            this.hashCode = builder.toHashCode();
        }
        return this.hashCode;
    }

    /**
     * 供子类重写的方法，用于加入属性值变化时的处理
     */
    protected void propertyValueChanged() {
        this.hashCode = null;
    }
}
