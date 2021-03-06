/*
* Copyright 2013-2021 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.query.customerInfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2021年3月2日
*/
@ApiModel(value="往来类别信息查询条件定义")
public class InfoTransactionQuery implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -664625682780889185L;
    private Long id;
    private String isRoot;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRoot() {
        return isRoot;
    }
    public void setRoot(String isRoot) {
        this.isRoot = isRoot;
    }
    
}
