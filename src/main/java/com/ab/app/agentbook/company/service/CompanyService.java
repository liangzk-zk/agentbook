/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.company.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ab.app.agentbook.company.info.CompanyInfo;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月30日
*/
public interface CompanyService {
    public CompanyInfo updateCompayQRcode(HttpServletRequest request,Long id, Long payType, MultipartFile poster);
    public CompanyInfo[] findByAll();
	public CompanyInfo findById(Long id);
	public CompanyInfo update(CompanyInfo companyInfo);
}
