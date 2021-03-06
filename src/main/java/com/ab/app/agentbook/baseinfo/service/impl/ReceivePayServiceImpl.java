/*
* Copyright 2013-2021 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ab.app.agentbook.baseinfo.dao.ReceivePayDao;
import com.ab.app.agentbook.baseinfo.entity.AbReceivePayItemEntity;
import com.ab.app.agentbook.baseinfo.info.ReceivePayInfo;
import com.ab.app.agentbook.baseinfo.service.ReceivePayService;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.data.crud.criteria.OrderBy;
import com.ab.app.agentbook.jpa.data.crud.CriteriaUtils;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2021年3月4日
*/
public class ReceivePayServiceImpl implements ReceivePayService,InitializingBean{
    private ReceivePayDao receivePayDao;

    public ReceivePayDao getReceivePayDao() {
        return receivePayDao;
    }

    public void setReceivePayDao(ReceivePayDao receivePayDao) {
        this.receivePayDao = receivePayDao;
    }
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(receivePayDao, "fundAccountDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
        }
    }
    @Override
    public int getReceivePayCount(Criterion[] criterions) {
        Specification<AbReceivePayItemEntity> spec = CriteriaUtils.getSpecification(AbReceivePayItemEntity.class,
                criterions);
        long count = receivePayDao.count(spec);
        return (int) count;
    }

    @Override
    public ReceivePayInfo[] getReceivePays(Criterion[] criterions, int startPosition, int maxResults,
            String orderBy) {
        OrderBy[] orders = null;
        if (StringUtils.isNotBlank(orderBy)) {
            int pos = orderBy.indexOf(' ');
            String field = orderBy.substring(0, pos);
            if (queryUserFieldMapping != null && queryUserFieldMapping.containsKey(field)) {
                field = queryUserFieldMapping.get(field);
            }
            String dir = orderBy.substring(pos + 1);
            // 先按照流程优先级升序排列，然后按照传入的排序字段排序，
            // 最后按日期降序排（如果排序字段不是日期）
            if ("asc".equalsIgnoreCase(dir)) {
                orders = OrderBy.orderBy(OrderBy.asc(field));
            } else {
                orders = OrderBy.orderBy(OrderBy.desc(field));
            }
        }
        Specification<AbReceivePayItemEntity> spec = CriteriaUtils.getSpecification(AbReceivePayItemEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<AbReceivePayItemEntity> list = receivePayDao.findList(spec, startPosition, maxResults, sort);
        List<ReceivePayInfo> result = new ArrayList<ReceivePayInfo>(list.size());
        for(AbReceivePayItemEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new ReceivePayInfo[result.size()]);
    }
    @Override
    @Transactional
    public ReceivePayInfo save(ReceivePayInfo info) {
        AbReceivePayItemEntity entity = warp(info);
        receivePayDao.persist(entity);
        receivePayDao.flush();
        return warp(entity);
    }
    @Override
    public ReceivePayInfo findById(Long id) {
        Optional<AbReceivePayItemEntity> entity = receivePayDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public ReceivePayInfo update(ReceivePayInfo info) {
        Optional<AbReceivePayItemEntity> entity = receivePayDao.findById(info.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(info);
            AbReceivePayItemEntity result = receivePayDao.save(e);
            receivePayDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removeReceivePay(ReceivePayInfo info) {
        receivePayDao.delete(warp(info));
        receivePayDao.flush();
    }
    @Transactional
    @Override
    public void removeReceivePay(Long id) {
        receivePayDao.deleteById(id);
        receivePayDao.flush();
    }
    private ReceivePayInfo warp(AbReceivePayItemEntity entity) {
        if(entity == null) {
            return null;
        }
        ReceivePayInfo info = new ReceivePayInfo();
        info.setId(entity.getId());
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setRemarks(entity.getRemarks());
        info.setReceivepaytype(entity.getReceivepaytype());
        info.setCreatedate(entity.getCreatedate());
        info.setModifydate(entity.getModifydate());
        info.setIscommission(entity.getIscommission());
        return info;
    }
    private AbReceivePayItemEntity warp(ReceivePayInfo info) {
        if(info == null) {
            return null;
        }
        AbReceivePayItemEntity entity = new AbReceivePayItemEntity();
        entity.setId(info.getId());
        entity.setCode(info.getCode());
        entity.setName(info.getName());
        entity.setRemarks(info.getRemarks());
        entity.setReceivepaytype(info.getReceivepaytype());
        entity.setCreatedate(info.getCreatedate());
        entity.setModifydate(info.getModifydate());
        entity.setIscommission(info.getIscommission());
        return entity;
    }

    @Override
    public ReceivePayInfo findByCode(String fundcode) {
        AbReceivePayItemEntity entity = receivePayDao.findByCode(fundcode);
        return warp(entity);
    }
}
