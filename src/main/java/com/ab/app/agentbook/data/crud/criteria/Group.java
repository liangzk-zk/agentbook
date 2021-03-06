package com.ab.app.agentbook.data.crud.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 约束条件组合
 * 
 *
 */
public class Group extends Criterion implements Iterable<Criterion> {
    private static final long serialVersionUID = -1261266538782487375L;

    protected List<Criterion> criteria = new ArrayList<Criterion>();
    protected Junction junction = Junction.AND;
    private transient Integer hashCode;

    /**
     * 空构造函数
     */
    public Group() {
        super();
    }

    /**
     * 清除组合中所包含的所有条件约束
     */
    public void clear() {
        criteria.clear();
        propertyValueChanged();
    }

    /**
     * 返回指定方式的条件组合
     * 
     * @param aJunction 条件间组合方式
     * @param aCriteria 待组合的条件
     */
    public Group(final Junction aJunction, final Criterion... aCriteria) {
        this.junction = aJunction;

        for (Criterion criterion : aCriteria) {
            add(criterion);
        }
    }

    /**
     * 返回多个排序字段的集合
     * 
     * @param orderBy
     * @return
     */
    public static String[] orderBy(String... orderBy) {
        return orderBy;
    }

    /**
     * 返回与方式的条件组合
     * 
     * @return
     */
    public static Group and(Class<?> baseType) {
        VerifiedGroup group = new VerifiedGroup();
        group.junction = Junction.AND;
        return group;
    }

    public static Group or(Class<?> baseType) {
        VerifiedGroup group = new VerifiedGroup();
        group.junction = Junction.OR;
        return group;
    }

    public static Group and(Class<?> baseType, final Criterion... criteria) {
        return new VerifiedGroup(baseType, Junction.AND, criteria);
    }

    public static Group and(Class<?> baseType, final Map<String, Object> map) {
        VerifiedGroup group = new VerifiedGroup();
        group.setJunction(Junction.AND);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(Comparison.eq(entry.getKey(), entry.getValue()));
        }
        return group;
    }

    public static Group or(Class<?> baseType, final Map<String, Object> map) {
        VerifiedGroup group = new VerifiedGroup();
        group.setJunction(Junction.OR);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(Comparison.eq(entry.getKey(), entry.getValue()));
        }
        return group;
    }

    public static Group and(Class<?> baseType, final Map<String, Object> map, Operator operator) {
        VerifiedGroup group = new VerifiedGroup();
        group.setJunction(Junction.AND);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(new Comparison(entry.getKey(), operator, entry.getValue()));
        }
        return group;
    }

    public static Group or(Class<?> baseType, final Map<String, Object> map, Operator operator) {
        VerifiedGroup group = new VerifiedGroup();
        group.setJunction(Junction.OR);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(new Comparison(entry.getKey(), operator, entry.getValue()));
        }
        return group;
    }

    public static Group or(Class<?> baseType, final Criterion... criteria) {
        return new Group(Junction.OR, criteria);
    }

    public static Group and() {
        Group group = new Group();
        group.junction = Junction.AND;
        return group;
    }

    /**
     * 返回或方式的条件组合
     * 
     * @return
     */
    public static Group or() {
        Group group = new Group();
        group.junction = Junction.OR;
        return group;
    }

    /**
     * 返回多个约束条件以与方式形成的条件组合
     * 
     * @param criteria
     * @return
     */
    public static Group and(Criterion... criteria) {
        return new Group(Junction.AND, criteria);
    }

    /**
     * 返回以与方式形成的条件组合
     * 
     * @param map 数据值集合
     * @return
     */
    public static Group and(final Map<String, Object> map) {
        Group group = new Group();
        group.setJunction(Junction.AND);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(Comparison.eq(entry.getKey(), entry.getValue()));
        }
        return group;
    }

    /**
     * 返回以或方式形成的条件组合
     * 
     * @param map 数据值集合
     * @return
     */
    public static Group or(final Map<String, Object> map) {
        Group group = new Group();
        group.setJunction(Junction.OR);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(Comparison.eq(entry.getKey(), entry.getValue()));
        }
        return group;
    }

    public static Group and(final Map<String, Object> map, Operator operator) {
        Group group = new Group();
        group.setJunction(Junction.AND);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(new Comparison(entry.getKey(), operator, entry.getValue()));
        }
        return group;
    }

    public static Group or(final Map<String, Object> map, Operator operator) {
        Group group = new Group();
        group.setJunction(Junction.OR);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            group.add(new Comparison(entry.getKey(), operator, entry.getValue()));
        }
        return group;
    }

    /**
     * 返回多个约束条件以或方式组合后的条件集合
     * 
     * @param criteria
     * @return
     */
    public static Group or(final Criterion... criteria) {
        return new Group(Junction.OR, criteria);
    }

    public Group add(Criterion criterion) {
        criteria.add(criterion);
        propertyValueChanged();
        return this;
    }

    /**
     * 向条件组合中添加针对指定字段的查询条件
     * 
     * @param name
     * @param operator
     * @param value
     * @return
     */
    public Group add(String name, Operator operator, Object value) {
        add(new Comparison(name, operator, value));
        return this;
    }

    public Group between(String name, Object value1, Object value2) {
        add(new Between(name, value1, value2));
        return this;
    }

    public Group eq(String name, Object value) {
        add(new Comparison(name, Operator.EQ, value));
        return this;
    }

    public Group ne(String name, Object value) {
        add(new Comparison(name, Operator.NE, value));
        return this;
    }

    public Group gt(String name, Object value) {
        add(new Comparison(name, Operator.GT, value));
        return this;
    }

    public Group lt(String name, Object value) {
        add(new Comparison(name, Operator.LT, value));
        return this;
    }

    public Group ge(String name, Object value) {
        add(new Comparison(name, Operator.GE, value));
        return this;
    }

    public Group le(String name, Object value) {
        add(new Comparison(name, Operator.LE, value));
        return this;
    }

    public String toString() {
        return "(" + junction + " " + criteria + ")";
    }

    /**
     * 返回所包含的条件集合
     * 
     * @return
     */
    public List<Criterion> getCriteria() {
        return criteria;
    }

    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }

    public int size() {
        return criteria.size();
    }

    /**
     * 返回条件集合中条件间的连接关系
     * 
     * @return
     */
    public Junction getJunction() {
        return junction;
    }

    /**
     * 设置条件集合中条件间的连接关系
     * 
     * @param junction
     */
    public void setJunction(Junction junction) {
        this.junction = junction;
        propertyValueChanged();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Group group = (Group) super.clone();
        if (criteria != null) {
            List<Criterion> list = new ArrayList<Criterion>();
            for (Criterion criterion : criteria) {
                list.add((Criterion) criterion.clone());
            }
            group.criteria = list;
        }
        return group;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        Group g = (Group) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(junction, g.junction);
        builder.append(criteria, g.criteria);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            HashCodeBuilder builder = new HashCodeBuilder();
            builder.append(junction == null ? "[]" : junction.name());
            builder.append(criteria);
            hashCode = builder.toHashCode();
        }

        return hashCode;
    }

    /**
     * 供子类重写的方法，用于加入属性值变化时的处理
     */
    protected void propertyValueChanged() {
        this.hashCode = null;
    }
}
