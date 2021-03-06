package com.ab.app.agentbook.data.crud.criteria;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 定义两个字段间关系的约束条件<br>
 *
 */
public class PropertyExpression extends Criterion {
    private static final long serialVersionUID = 4455623272702052637L;
    private String name;
    private String otherName;
    private Operator operator;
    private transient Integer hashCode = null;

    public PropertyExpression() {
        super();
    }

    public PropertyExpression(String name, String otherName, Operator operator) {
        super();
        this.name = name;
        this.otherName = otherName;
        this.operator = operator;
    }

    /**
     * 返回条件左侧的字段名称
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置条件左侧的字段名称
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        propertyValueChanged();
    }

    /**
     * 返回条件右侧的字段名称
     * 
     * @return
     */
    public String getOtherName() {
        return otherName;
    }

    /**
     * 设置条件右侧的字段名称
     * 
     * @param otherName
     */
    public void setOtherName(String otherName) {
        this.otherName = otherName;
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

    @Override
    public String toString() {
        return name + "_" + operator + "_" + otherName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyExpression)) {
            return false;
        }
        PropertyExpression c = (PropertyExpression) obj;
        if (this.hashCode != null && c.hashCode != null && !this.hashCode.equals(c.hashCode)) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.operator, c.operator).append(this.name, c.name).append(this.otherName, c.otherName);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        if (this.hashCode == null) {
            HashCodeBuilder builder = new HashCodeBuilder();
            builder.append(operator == null ? "[]" : operator.name()).append(name).append(otherName);
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
