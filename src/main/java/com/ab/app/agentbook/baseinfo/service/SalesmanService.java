/*
* Copyright 2013-2021 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.baseinfo.service;

import com.ab.app.agentbook.baseinfo.info.SalesmanInfo;
import com.ab.app.agentbook.data.crud.criteria.Criterion;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2021年3月4日
*/
public interface SalesmanService {

    SalesmanInfo findByCode(String fundcode);

    void removeSalesman(Long id);

    void removeSalesman(SalesmanInfo info);

    SalesmanInfo update(SalesmanInfo info);

    SalesmanInfo findById(Long id);

    SalesmanInfo save(SalesmanInfo info);

    SalesmanInfo[] getSalesmans(Criterion[] criterions, int startPosition, int maxResults, String orderBy);

    int getSalesmanCount(Criterion[] criterions);

}
