package com.ab.app.agentbook.rest.query;

import java.io.Serializable;
import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 信息查询结果
 */
@ApiModel(value = "信息查询结果")
public class InfoQueryResult<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -252287512417636103L;

    private int total = 0;
    private T[] datas;

    /**
     * 返回满足查询条件的结果总数
     * 
     * @return 满足查询条件的结果总数
     */
    @ApiModelProperty(value = "满足查询条件的查询结果总数",example="0")
    public int getTotal() {
        return total;
    }

    /**
     * 设置满足查询条件的结果总数
     * 
     * @param total 满足查询条件的结果总数
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 返回查询的结果数据集合，一定不为null
     * 
     * @return 查询的结果数据集合
     */
    @ApiModelProperty(value = "查询结果数据集合，一定不为null")
    public T[] getDatas() {
        return datas == null ? (T[])new ArrayList<T>().toArray() : datas;
    }

    /**
     * 设置查询的结果数据集合
     * 
     * @param datas 查询的结果数据集合
     */
    public void setDatas(T[] datas) {
        this.datas = datas;
    }

}