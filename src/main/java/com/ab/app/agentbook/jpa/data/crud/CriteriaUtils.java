package com.ab.app.agentbook.jpa.data.crud;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.ab.app.agentbook.data.crud.criteria.Between;
import com.ab.app.agentbook.data.crud.criteria.Comparison;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.data.crud.criteria.Group;
import com.ab.app.agentbook.data.crud.criteria.Junction;
import com.ab.app.agentbook.data.crud.criteria.Operator;
import com.ab.app.agentbook.data.crud.criteria.OrderBy;
import com.ab.app.agentbook.data.crud.criteria.OrderDirection;
import com.ab.app.agentbook.data.crud.criteria.PropertyExpression;
import com.ab.app.agentbook.data.crud.criteria.SQLCriterion;
import com.ab.app.agentbook.data.crud.criteria.Select;
import com.ab.app.agentbook.data.crud.criteria.VerifiedBetween;
import com.ab.app.agentbook.data.crud.join.EntityJoin;
import com.ab.app.agentbook.data.crud.join.Join;
import com.ab.app.agentbook.data.crud.join.JoinType;
import com.ab.app.agentbook.data.crud.join.RelationshipFetch;
import com.ab.app.agentbook.data.crud.join.RelationshipJoin;
import com.ab.app.agentbook.data.crud.join.SimpleRelationshipJoin;


/**
 * 查询约束转换工具类，用于简化不同规范间查询约束的转换
 * 
 */
public class CriteriaUtils {

    protected static Logger logger = LoggerFactory.getLogger(CriteriaUtils.class);

    protected CriteriaUtils() {
        super();
    }
    
    /**
     * 将排序设置{@link OrderBy}转换为对应的{@link Sort}对象
     * 
     * @param orders
     * @return
     */
    public static Sort getSort(OrderBy... orders) {
        if (orders == null || orders.length == 0) {
            return null;
        }
        Sort.Order[] list = new Sort.Order[orders.length];
        for (int i = 0; i < orders.length; i++) {
            list[i] = new Sort.Order(
                    orders[i].getDirection() == OrderDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
                    orders[i].getName());
        }
        return Sort.by(list);
    }

    /**
     * 将查询约束条件{@link Criterion}转换为对应的{@link Specification}对象
     * 
     * @param clazz
     * @param criteria
     * @return
     */
    public static <T> Specification<T> getSpecification(Class<T> clazz, final Criterion... criteria) {
        if (criteria == null || criteria.length == 0) {
            return null;
        }
        return new Specification<T>() {
            private static final long serialVersionUID = -5190514531564739306L;

			@Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Group group = (criteria.length == 1 && criteria[0] instanceof Group) ? (Group) criteria[0]
                        : Group.and(criteria);
                Predicate[] restrictions = buildGroup(group, root, cb);
                if (restrictions == null || restrictions.length == 0) {
                    return cb.conjunction();
                } else if (restrictions.length == 1) {
                    return restrictions[0];
                } else {
                    Junction junction = group.getJunction();
                    return junction == Junction.AND ? cb.and(restrictions) : cb.or(restrictions);
                }
            }

        };
    }

    public static String constuctWhereClause(final Group group) {
        String whereClause = "";
        if (group == null || group.size() > 0) {
            whereClause = constructWhereClauseString(group, false);
        }
        return whereClause;
    }

    public static String constructWhereClauseString(Group group, boolean parens) {
        StringBuilder builder = new StringBuilder(255);
        if (group == null || group.size() == 0) {
            return "";
        } else if (group.size() == 1) {
            Criterion criterion = group.iterator().next();
            if (criterion instanceof Group) {
                Group innerGroup = (Group) criterion;
                if (innerGroup.size() == 0) {
                    return "";
                }
            }
        }
        builder.append(" WHERE ");
        constructWhereClauseString(builder, group, false, null);
        return builder.toString();
    }

    public static void constructWhereClauseString(StringBuilder builder, Group group, boolean parens,
            Set<String> names) {

        if (names == null) {
            names = new HashSet<String>();
        }

        if (parens) {
            builder.append(" ( ");
        }
        if (group.size() == 1) {
            Criterion criterion = group.iterator().next();
            if (criterion instanceof Group) {
                constructWhereClauseString(builder, (Group) criterion, true, names);
            } else if (criterion instanceof Comparison) {
                addComparisonToQueryString((Comparison) criterion, builder, names);
            } else if (criterion instanceof PropertyExpression) {
                addPropertyComparisonToQueryString((PropertyExpression) criterion, builder);
            } else if (criterion instanceof SQLCriterion) {
                addSQLCriterionToQueryString((SQLCriterion) criterion, builder);
            }
        } else {
            int size = group.size();
            int index = 0;
            for (Criterion criterion : group) {
                index++;
                if (criterion instanceof Group) {
                    constructWhereClauseString(builder, (Group) criterion, true, names);
                } else if (criterion instanceof Comparison) {
                    addComparisonToQueryString((Comparison) criterion, builder, names);
                } else if (criterion instanceof PropertyExpression) {
                    addPropertyComparisonToQueryString((PropertyExpression) criterion, builder);
                } else if (criterion instanceof SQLCriterion) {
                    addSQLCriterionToQueryString((SQLCriterion) criterion, builder);
                }
                if (index != size) {
                    builder.append(" ");
                    builder.append(group.getJunction());
                    builder.append(" ");
                }
            }
        }
        if (parens) {
            builder.append(" ) ");
        }
    }

    public static void addComparisonToQueryString(Comparison comparison, StringBuilder builder, Set<String> names) {

        String var = ":" + ditchDot(comparison.getName());
        var = ensureUnique(names, var);

        if (comparison.isObjectIdentity()) {
            builder.append(comparison.getName());
            builder.append("=");
            builder.append(comparison.getValue());
            return;
        }

        if (comparison.getValue() != null) {
            final String sOperator = comparison.getOperator().getOperator();
            if (!comparison.isCaseSensitive()) {
                builder.append(" UPPER( ");
            }
            if (!comparison.isAlias()) {
                builder.append(" o.");
            } else {
                builder.append(" ");
            }
            builder.append(comparison.getName());
            if (!comparison.isCaseSensitive()) {
                builder.append(" ) ");
            }

            builder.append(" ");
            builder.append(sOperator);
            builder.append(" ");

            if (!comparison.isCaseSensitive()) {
                builder.append(" UPPER( ");
            }
            if (comparison instanceof Between || comparison instanceof VerifiedBetween) {
                builder.append(var).append("_1");
                builder.append(" ");
                builder.append("and ").append(var).append("_2");
            } else if (comparison.getOperator() == Operator.IN) {
                builder.append(" (");
                builder.append(var);
                builder.append(") ");
            } else {
                builder.append(var);
            }
            builder.append(" ");
            if (!comparison.isCaseSensitive()) {
                builder.append(" ) ");
            }

        } else {
            if (!comparison.isAlias()) {
                builder.append(" o.");
            } else {
                builder.append(" ");
            }
            builder.append(comparison.getName());
            if (comparison.getOperator() == Operator.EQ) {
                builder.append(" is null ");
            } else if (comparison.getOperator() == Operator.NE) {
                builder.append(" is not null ");
            }
        }
    }

    public static void addPropertyComparisonToQueryString(PropertyExpression comparison, StringBuilder builder) {
        String sOperator = comparison.getOperator().getOperator();
        builder.append(comparison.getName());
        builder.append(' ');
        builder.append(sOperator);
        builder.append(' ');
        builder.append(comparison.getOtherName());
    }

    public static void addSQLCriterionToQueryString(SQLCriterion criterion, StringBuilder builder) {
        builder.append(criterion.getSql());
    }

    public static String constructOrderBy(OrderBy[] orderBys) {
        StringBuilder query = new StringBuilder(100);
        if (null != orderBys && orderBys.length > 0) {
            query.append(" ORDER BY ");

            for (int index = 0; index < orderBys.length; index++) {

                OrderBy cob = orderBys[index];
                if (cob == null) {
                    throw new IllegalStateException();
                }
                if (!cob.isCaseSensitive()) {
                    query.append("UPPER( ");
                }
                if (!cob.isAlias()) {
                    query.append("o." + cob.getName());
                } else {
                    query.append(cob.getName());
                }
                query.append(" ");
                if (!cob.isCaseSensitive()) {
                    query.append(") ");
                }
                query.append(cob.getDirection().toString());
                if (index + 1 < orderBys.length) {
                    query.append(", ");
                }
            }
        }
        return query.toString();
    }

    public static String constructSelect(Select[] selects, String newSelectStatement, String entityName,
            String instanceName, boolean distinctFlag) {
        StringBuilder query = new StringBuilder(255);
        query.append("SELECT ");

        if (newSelectStatement != null) {
            query.append(newSelectStatement);
            return query.toString();
        }
        query.append((distinctFlag ? " DISTINCT " : " "));
        query.append(instanceName);

        if (selects == null || selects.length == 0) {
            return query.toString();
        } else {
            query.append(", ");
            for (Select select : selects) {
                query.append((select.isDistinct() ? " DISTINCT " : " "));
                query.append(select.getName());
                query.append(", ");
            }
            return query.substring(0, query.length() - 2);
        }
    }

    public static String constructJoins(Join[] joins) {
        if (joins == null || joins.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(255);
        for (Join join : joins) {
            if (join instanceof RelationshipJoin) {
                RelationshipJoin rj = (RelationshipJoin) join;

                if (rj.getJoinType() == JoinType.LEFT) {
                    builder.append(" left ");
                }

                if (rj instanceof RelationshipFetch) {
                    builder.append(" join fetch ");
                } else if (rj instanceof SimpleRelationshipJoin) {
                    builder.append(" join ");
                }

                builder.append(rj.isAliasedRelationship() ? "" : "o.");
                builder.append(rj.getRelationshipProperty());
                builder.append(" ");
                builder.append(rj.getAlias().equals("") ? rj.getDefaultAlias() : rj.getAlias());
            }
        }

        return builder.toString();
    }

    public static String constructFrom(Class<?> type, Join... joins) {
        final StringBuilder sbquery = new StringBuilder(35);
        sbquery.append(" FROM ");
        sbquery.append(GenericDaoUtils.getEntityName(type));
        sbquery.append(" o ");
        if (joins == null || joins.length == 0) {
            return sbquery.toString();
        }
        for (Join join : joins) {
            if (join instanceof EntityJoin) {
                EntityJoin ej = (EntityJoin) join;
                sbquery.append(" , ");
                sbquery.append(ej.getName());
                sbquery.append(" ");
                sbquery.append(ej.getAlias());
            }
        }
        return sbquery.toString();
    }

    public static String ensureUnique(Set<String> names, String name) {
        if (names.contains(name)) {
            int index = 0;
            String tempVar = null;
            while (true) {
                tempVar = name + "_" + index;
                if (!names.contains(tempVar)) {
                    break;
                }
                index++;
            }
            name = tempVar;
        }
        names.add(name);
        return name;
    }

    public static String ditchDot(String propName) {
        propName = propName.replace('.', '_');
        return propName;
    }

    public static String createQuery(Class<?> clazz, Select[] selects, String newSelectStatement, boolean distinctFlag,
            OrderBy[] orderBy, Join[] joins, final Group group) {
        StringBuilder sbQuery = new StringBuilder(255);
        final String sQuery = sbQuery
                .append(CriteriaUtils.constructSelect(selects, newSelectStatement, GenericDaoUtils.getEntityName(clazz),
                        "o", distinctFlag))
                .append(CriteriaUtils.constructFrom(clazz, joins)).append(CriteriaUtils.constructJoins(joins))
                .append(CriteriaUtils.constuctWhereClause(group)).append(CriteriaUtils.constructOrderBy(orderBy))
                .toString();
        logger.debug(String.format("Query %s ", sQuery));
        return sQuery;
    }

    public static String createCountQuery(final Group group, Class<?> type, boolean distinct) {
        final StringBuilder sbquery = new StringBuilder(
                "SELECT count(" + (distinct ? "DISTINCT " : "") + "o ) " + " FROM ");
        sbquery.append(GenericDaoUtils.getEntityName(type));
        sbquery.append(" ");
        sbquery.append("o").append(" ").append(CriteriaUtils.constuctWhereClause(group));
        return sbquery.toString();
    }

    protected static <T> Predicate[] buildGroup(Group group, Root<T> root, CriteriaBuilder cb) {
        if (group == null || group.size() == 0) {
            return new Predicate[0];
        }
        List<Predicate> predicates = new ArrayList<Predicate>(group.size());
        for (Criterion c : group) {
            if (c instanceof Group) {
                Junction j = ((Group) c).getJunction();
                Predicate[] restrictions = buildGroup((Group) c, root, cb);
                predicates.add(j == Junction.AND ? cb.and(restrictions) : cb.or(restrictions));
            } else if (c instanceof Comparison) {
                predicates.add(buildComparison((Comparison) c, root, cb));
            } else if (c instanceof PropertyExpression) {
                predicates.add(buildPropertyComparison((PropertyExpression) c, root, cb));
            } else if (c instanceof SQLCriterion) {
                throw new IllegalArgumentException("SQLCriterion is unsupport");
                // addSQLCriterionToQueryString((SQLCriterion)
                // criterion,
                // builder);
            }
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected static <T> Predicate buildComparison(Comparison criterion, Root<T> root, CriteriaBuilder cb) {
        String name = criterion.getName();
        Operator op = criterion.getOperator();
        Object value = criterion.getValue();
        // nested path translate, 如Task的名为"user.name"的filedName,
        // 转换为Task.user.name属性
        String[] names = StringUtils.split(name, ".");
        Path expression = null;
        if (names != null && names.length > 0) {
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        }

        switch (op) {
        case EQ:
            return cb.equal(expression, value);
        case NE:
            return cb.notEqual(expression, value);
        case LE:
            return cb.lessThanOrEqualTo(expression, (Comparable) value);
        case GE:
            return cb.greaterThanOrEqualTo(expression, (Comparable) value);
        case GT:
            return cb.greaterThan(expression, (Comparable) value);
        case LT:
            return cb.lessThan(expression, (Comparable) value);
        case BETWEEN:
            return cb.between(expression, (Comparable) value, (Comparable) ((Between) criterion).getValue2());
        case IN: 
            In in = cb.in(expression);
            if (value == null || !(value instanceof List) || ((List) value).size() == 0) {
                throw new IllegalArgumentException(" param of in require a not empty list ");
            }
            for (Object v : (List) value) {
                in.value(v);
            }
            return in;
        case LIKE:
            return cb.like(expression, (String) value);
        case LIKE_START:
            return cb.like(expression, value == null ? "%" : value + "%");
        case LIKE_END:
            return cb.like(expression, value == null ? "%" : "%" + value);
        case LIKE_CONTAINS:
            return cb.like(expression, value == null ? "%" : "%" + value + "%");
        case ILIKE:
            throw new IllegalArgumentException("unsupported operator: " + op);
        case IS_NULL:
            return cb.isNull(expression);
        case IS_NOT_NULL:
            return cb.isNotNull(expression);
        case NOT:
            return cb.not(buildComparison((Comparison) value, root, cb));
        default:
            throw new IllegalArgumentException("unsupported operator: " + op);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected static <T> Predicate buildPropertyComparison(PropertyExpression criterion, Root<T> root,
            CriteriaBuilder cb) {
        String name = criterion.getName();
        Operator op = criterion.getOperator();
        String otherName = criterion.getOtherName();
        // nested path translate, 如Task的名为"user.name"的filedName,
        // 转换为Task.user.name属性
        String[] names = StringUtils.split(name, ".");
        Path expression = root.get(names[0]);
        for (int i = 1; i < names.length; i++) {
            expression = expression.get(names[i]);
        }
        names = StringUtils.split(otherName, ".");
        Path otherExpression = root.get(names[0]);
        for (int i = 1; i < names.length; i++) {
            otherExpression = otherExpression.get(names[i]);
        }
        switch (op) {
        case EQ:
            return cb.equal(expression, otherExpression);
        case NE:
            return cb.notEqual(expression, otherExpression);
        case LE:
            return cb.lessThanOrEqualTo(expression, otherExpression);
        case GE:
            return cb.greaterThanOrEqualTo(expression, otherExpression);
        case GT:
            return cb.greaterThan(expression, otherExpression);
        case LT:
            return cb.lessThan(expression, otherExpression);
        default:
            throw new IllegalArgumentException("unsupported operator: " + op);
        }
    }
}
