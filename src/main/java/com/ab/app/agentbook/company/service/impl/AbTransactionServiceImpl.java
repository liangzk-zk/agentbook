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

import com.ab.app.agentbook.company.dao.AbTransactionDao;
import com.ab.app.agentbook.company.entity.AbTransactionEntity;
import com.ab.app.agentbook.company.info.AbTransactionInfo;
import com.ab.app.agentbook.company.service.AbTransactionService;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月12日
*/
public class AbTransactionServiceImpl implements AbTransactionService{
    private AbTransactionDao abTransactionDao;
    public AbTransactionDao getAbTransactionDao() {
        return abTransactionDao;
    }


    public void setAbTransactionDao(AbTransactionDao abTransactionDao) {
        this.abTransactionDao = abTransactionDao;
    }
    
//    
//    @Override
//    public CarouselInfo[] getCarousels(Criterion[] criterions, int startPosition, int maxResults, String orderBy) {
//        OrderBy[] orders = null;
//        if (StringUtils.isNotBlank(orderBy)) {
//            int pos = orderBy.indexOf(' ');
//            String field = orderBy.substring(0, pos);
//            String dir = orderBy.substring(pos + 1);
//            // 先按照流程优先级升序排列，然后按照传入的排序字段排序，
//            // 最后按日期降序排（如果排序字段不是日期）
//            if ("asc".equalsIgnoreCase(dir)) {
//                orders = OrderBy.orderBy(OrderBy.asc(field));
//            } else {
//                orders = OrderBy.orderBy(OrderBy.desc(field));
//            }
//        }
//        Specification<AbTransactionEntity> spec = CriteriaUtils.getSpecification(AbTransactionEntity.class,
//                criterions);
//        Sort sort = CriteriaUtils.getSort(orders);
//        List<AbTransactionEntity> list = carouselDao.findList(spec, startPosition, maxResults, sort);
//        List<CarouselInfo> result = new ArrayList<CarouselInfo>();
//        for(AbTransactionEntity entity:list) {
//            result.add(warp(entity));
//        }
//        return result.toArray(new CarouselInfo[result.size()]);
//    }

    @Override
    public AbTransactionInfo[] findByParentId(long i) {
        List<AbTransactionEntity> entitys = abTransactionDao.findByParentId(i);
        List<AbTransactionInfo> result = new ArrayList<AbTransactionInfo>();
        for(AbTransactionEntity entity : entitys) {
            result.add(warp(entity));
        }
        return result.toArray(new AbTransactionInfo[result.size()]);
    }
    @Override
    public AbTransactionInfo findById(long i) {
        Optional<AbTransactionEntity> entity = abTransactionDao.findById(i);
        return warp(entity.get());
    }
    private AbTransactionInfo warp(AbTransactionEntity entity) {
        if(entity==null) {
            return null;
        }
        AbTransactionInfo info = new AbTransactionInfo();
        info.setId(entity.getId());
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setParentId(entity.getParentId());
        info.setMnemonicCode(entity.getMnemonicCode());
        info.setRemarks(entity.getRemarks());
        return info;
    }
    private AbTransactionEntity warp(AbTransactionInfo info) {
        if(info==null) {
            return null;
        }
        AbTransactionEntity entity = new AbTransactionEntity();
        entity.setCode(info.getCode());
        entity.setName(info.getName());
        entity.setParentId(info.getParentId());
        entity.setMnemonicCode(info.getMnemonicCode());
        entity.setRemarks(info.getRemarks());
        return entity;
    }

    @Override
    public AbTransactionInfo findByCode(String code) {
        AbTransactionEntity entity = abTransactionDao.findByCode(code);
        return warp(entity);
    }


    @Override
    public AbTransactionInfo save(AbTransactionInfo info) {
        AbTransactionEntity entity = warp(info);
        if(entity==null) {
            return null;
        }
        abTransactionDao.persist(entity);
        abTransactionDao.flush();
        return warp(entity);
    }
}
