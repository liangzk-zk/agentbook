/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.company.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ab.app.agentbook.company.dao.CompanyDao;
import com.ab.app.agentbook.company.entity.XyCompanyEntity;
import com.ab.app.agentbook.company.info.CompanyInfo;
import com.ab.app.agentbook.company.service.CompanyService;
import com.ab.app.agentbook.file.sercice.FileService;


/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月30日
*/
public class CompanyServiceImpl implements CompanyService{
    private CompanyDao companyDao;
    private FileService fileStoreService;

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }
    
    public FileService getFileStoreService() {
        return fileStoreService;
    }

    public void setFileStoreService(FileService fileStoreService) {
        this.fileStoreService = fileStoreService;
    }

    @Override
    public CompanyInfo updateCompayQRcode(HttpServletRequest request,Long id, Long payType, MultipartFile file) {
        Optional<XyCompanyEntity> entity = companyDao.findById(id);
        if(entity==null) {
            return null;
        }
        String qrcodePath = fileStoreService.uploadQRcode(request,payType,file);
        return entity.map(e -> {
            if(payType==0) {
                e.setWechatpay(qrcodePath);
            }else if(payType==1) {
                e.setAlipay(qrcodePath);
            }else if(payType==2) {
                e.setTelephone(qrcodePath);
            }else if(payType==3) {
                e.setSynopsis(qrcodePath);
            }
            companyDao.merge(e);
            companyDao.flush();
            return warp(e);
            }).orElse(null);
    }

    private CompanyInfo warp(XyCompanyEntity entity) {
        if(entity==null) {
            return null;
        }
        CompanyInfo com = new CompanyInfo();
        com.setId(entity.getId());
        com.setCardid(entity.getCardid());
        com.setPayee(entity.getPayee());
        com.setAlipay(entity.getAlipay());
        com.setWechatpay(entity.getWechatpay());
        com.setBankname(entity.getBankname());
        com.setCompanyname(entity.getCompanyname());
        com.setTelephone(entity.getTelephone());
        com.setEnabled(entity.getEnabled());
        com.setStatus(entity.getStatus());
        com.setSynopsis(entity.getSynopsis());
        return com;
    }
    private XyCompanyEntity warp(CompanyInfo compantInfo) {
        if(compantInfo==null) {
            return null;
        }
        XyCompanyEntity entity = new XyCompanyEntity();
        entity.setId(compantInfo.getId());
        entity.setCardid(compantInfo.getCardid());
        entity.setPayee(compantInfo.getPayee());
        entity.setAlipay(compantInfo.getAlipay());
        entity.setWechatpay(compantInfo.getWechatpay());
        entity.setBankname(compantInfo.getBankname());
        entity.setCompanyname(compantInfo.getCompanyname());
        entity.setTelephone(compantInfo.getTelephone());
        entity.setEnabled(compantInfo.getEnabled());
        entity.setStatus(compantInfo.getStatus());
        entity.setSynopsis(compantInfo.getSynopsis());
        return entity;
    }

    @Override
    public CompanyInfo[] findByAll() {
        List<XyCompanyEntity> result = companyDao.findAll();
        List<CompanyInfo> list = new ArrayList<CompanyInfo>();
        for(XyCompanyEntity e:result) {
            list.add(warp(e));
        }
        return result==null?null:list.toArray(new CompanyInfo[list.size()]);
    }

	@Override
	public CompanyInfo findById(Long id) {
		Optional<XyCompanyEntity> entity = companyDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
	}

	@Override
	public CompanyInfo update(CompanyInfo companyInfo) {
		Optional<XyCompanyEntity> entity = companyDao.findById(companyInfo.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(companyInfo);
            XyCompanyEntity result = companyDao.save(e);
            companyDao.flush();
            return warp(result);
            }).orElse(null);
	}
}
