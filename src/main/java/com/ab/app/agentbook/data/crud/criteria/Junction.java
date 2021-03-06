package com.ab.app.agentbook.data.crud.criteria;

import java.io.Serializable;

/**
 * 查询条件间的连接关系
 *
 */
public enum Junction implements Serializable {
    /**
     * 以与方式进行连接
     */
    AND,
    /**
     * 以或方式进行连接
     */
    OR;
}
