/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.company.dao;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.company.entity.XyCompanyEntity;
import com.ab.app.agentbook.jpa.GenericRepository;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月30日
*/
@Repository
public interface CompanyDao extends GenericRepository<XyCompanyEntity, Long>{

}
