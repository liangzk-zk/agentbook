package com.ab.app.agentbook.rest.query;

import java.io.Serializable;

import com.ab.app.agentbook.jpa.ws.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 信息查询条件定义基础类
 */
@ApiModel(value="信息查询条件定义")
public class InfoQuery implements Serializable{
    
    private static final long serialVersionUID = -8058932020348985566L;

    private Expression[] expressions;
    private int startPosition = 0;
    private int maxResults = -1;
    private String orderBy;

    @ApiModelProperty(value = "查询条件集合",example = "")
    public Expression[] getExpressions() {
        return expressions;
    }

    public void setExpressions(Expression[] expressions) {
        this.expressions = expressions;
    }

    @ApiModelProperty(value = "返回记录前跳过的记录条数",required =true,allowEmptyValue = false, example="0")
    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    @ApiModelProperty(value = "返回的结果记录条数，如果传入-1则返回所有的查询结果",required =true,allowEmptyValue = false,example="-1")
    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    @ApiModelProperty(value = "查询结果的排序设置，格式为\"字段 排序设置\"，其中排序设置的值为ASC或DESC，如果涉及多个字段，则多段定义间以，分隔",example = "name asc")
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
