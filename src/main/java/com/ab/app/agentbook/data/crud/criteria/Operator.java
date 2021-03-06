package com.ab.app.agentbook.data.crud.criteria;

import java.io.Serializable;

/**
 * 约束类型
 * 
 *
 */
public enum Operator implements Serializable {
    /**
     * 等于
     */
    EQ,
    /**
     * 不等于
     */
    NE,
    /**
     * 小于等于
     */
    LE,
    /**
     * 大于等于
     */
    GE,
    /**
     * 大于
     */
    GT,
    /**
     * 小于
     */
    LT,
    /**
     * 取值范围
     */
    BETWEEN,
    /**
     * 值列表
     */
    IN,
    /**
     * 类似
     */
    LIKE,
    /**
     * 字符串开头类似
     */
    LIKE_START,
    /**
     * 字符串结尾类似
     */
    LIKE_END,
    /**
     * 字符串内容类似
     */
    LIKE_CONTAINS,
    /**
     * 忽略大小写的类似
     */
    ILIKE,
    /**
     * 为空
     */
    IS_NULL,
    /**
     * 不为空
     */
    IS_NOT_NULL,
    /**
     * NOT前缀
     */
    NOT;

    private static String[] operators = { "=", "<>", "<=", ">=", ">", "<", "BETWEEN", "IN", "LIKE", "LIKE", "LIKE",
            "LIKE", "LIKE", "IS NULL", "IS NOT NULL", "NOT" };

    /**
     * 返回与指定约束类型相对应的查询操作符
     * 
     * @return
     */
    public String getOperator() {
        return operators[this.ordinal()];
    }
}
