package com.ab.app.agentbook.util;

import java.util.Map;

import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.jpa.ws.Expression;
import com.ab.app.agentbook.jpa.ws.ExpressionUtils;

public class CriterionUtil {
	/**
     *  待办表达式在转换成查询约束条件
     * @param expressions 表达式
     * @return 查询约束条件
     */
    public static Criterion[] toCriterions(Expression []expressions,Map<String, String> queryUserFieldMapping) {
        Criterion[] criterions;
        if (expressions != null) {
            criterions = ExpressionUtils.getCriteria(expressions, queryUserFieldMapping);
        } else {
            criterions = new Criterion[0];
        }
        return criterions;
    }
}
