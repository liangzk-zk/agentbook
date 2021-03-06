package com.ab.app.agentbook.controller.criteron;

/**
 * 查询条件类型 <br>
 *
 */
public interface Operator {
    /**
     * 等于
     */
    public String EQ = "eq";
    /**
     * 不等于
     */
    public String NE = "ne";
    /**
     * 小于等于
     */
    public String LE = "le";
    /**
     * 大于等于
     */
    public String GE = "ge";
    /**
     * 大于
     */
    public String GT = "gt";
    /**
     * 小于
     */
    public String LT = "lt";
    /**
     * 在..之间
     */
    public String BETWEEN = "between";
    /**
     * in
     */
    public String IN = "in";
    /**
     * 类似
     */
    public String LIKE = "like";
    /**
     * 忽略大小写的类似
     */
    public String ILIKE = "ilike";
    /**
     * 字符串开头类似
     */
    public String LIKE_START = "like_start";
    /**
     * 字符串结尾类似
     */
    public String LIKE_END = "like_end";
    /**
     * 字符串内容类似
     */
    public String LIKE_CONTAINS = "like_contains";
    /**
     * 值为null
     */
    public String IS_NULL = "is_null";
    /**
     * 值不为null
     */
    public String IS_NOT_NULL = "is_not_null";
    /**
     * NOT前缀
     */
    public String NOT = "not";
    /**
     * 多个条件间以与方式组合
     */
    public String AND = "and";
    /**
     * 多个条件以或方式组合
     */
    public String OR = "or";
}
