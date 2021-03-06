/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.company.service;

import java.util.List;

import com.ab.app.agentbook.company.info.AbTransactionInfo;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月12日
*/
public interface AbTransactionService {
    /**
     * 异步获取往来类别信息
     * @param i
     *          根节点ID
     * @return
     */
    public AbTransactionInfo[] findByParentId(long i);
    /**
     * 获取根节点信息
     * @param id
     * @return
     */
    public AbTransactionInfo findById(long id);
    /**
     * 根据CODE获取节点信息
     * @param code 
     *          往来类别编码
     * @return 
     */
    public AbTransactionInfo findByCode(String code);
    /**
     * 保存往来类别信息
     * @param info
     * @return
     */
    public AbTransactionInfo save(AbTransactionInfo info);
}
