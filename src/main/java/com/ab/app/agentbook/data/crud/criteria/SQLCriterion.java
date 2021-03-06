package com.ab.app.agentbook.data.crud.criteria;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 直接指定sql片段的约束条件<br>
 *
 */
public class SQLCriterion extends Criterion {
    private static final long serialVersionUID = 157180369047901715L;
    private String sql;
    private transient Integer hashCode = null;

    public SQLCriterion() {
        super();
    }

    public SQLCriterion(String sql) {
        super();
        this.sql = sql;
    }

    /**
     * 返回用于设置约束条件的sql片段
     * 
     * @return
     */
    public String getSql() {
        return sql;
    }

    /**
     * 设置用于设置约束条件的sql片段
     * 
     * @param sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyExpression)) {
            return false;
        }
        SQLCriterion c = (SQLCriterion) obj;
        if (hashCode != null && c.hashCode != null && !hashCode.equals(c.hashCode)) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.sql, c.sql);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            HashCodeBuilder builder = new HashCodeBuilder();
            builder.append(sql);
            hashCode = builder.toHashCode();
        }
        return hashCode;
    }

    /**
     * 供子类重写的方法，用于加入属性值变化时的处理
     */
    protected void propertyValueChanged() {
        hashCode = null;
    }
}
