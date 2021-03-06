package com.ab.app.agentbook.data.crud.criteria;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Between extends Comparison {
    private static final long serialVersionUID = 5649875305992778747L;

    private Object value2;
    private transient Integer hashCode;

    public Between() {
        this.setOperator(Operator.BETWEEN);
    }

    public Between(String aName, Object aValue, Object aValue2) {
        super(aName, Operator.BETWEEN, aValue);
        this.value2 = aValue2;
    }

    public Between(String aName, Object aValue, Object aValue2, boolean alias) {
        super(aName, Operator.BETWEEN, aValue, alias);
        this.value2 = aValue2;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
        this.hashCode = null;
    }

    @Override
    public String toString() {
        return super.toString() + "_" + value2;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        return new EqualsBuilder().append(this.value2, ((Between) obj).value2).isEquals();
    }

    @Override
    public int hashCode() {
        if (this.hashCode == null) {
            HashCodeBuilder builder = new HashCodeBuilder();
            builder.append(super.hashCode());
            builder.append(value2);
            this.hashCode = builder.toHashCode();
        }
        return this.hashCode;
    }

    @Override
    protected void propertyValueChanged() {
        super.propertyValueChanged();
        this.hashCode = null;
    }
}
