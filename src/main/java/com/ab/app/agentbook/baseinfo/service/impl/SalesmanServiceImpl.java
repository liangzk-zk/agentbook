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

import com.ab.app.agentbook.baseinfo.dao.SalesmanDao;
import com.ab.app.agentbook.baseinfo.entity.AbSalesmanEntity;
import com.ab.app.agentbook.baseinfo.info.SalesmanInfo;
import com.ab.app.agentbook.baseinfo.service.SalesmanService;
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
public class SalesmanServiceImpl implements SalesmanService,InitializingBean{
    private SalesmanDao salesmanDao;

    public SalesmanDao getSalesmanDao() {
        return salesmanDao;
    }

    public void setSalesmanDao(SalesmanDao salesmanDao) {
        this.salesmanDao = salesmanDao;
    }
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(salesmanDao, "fundAccountDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
        }
    }
    @Override
    public int getSalesmanCount(Criterion[] criterions) {
        Specification<AbSalesmanEntity> spec = CriteriaUtils.getSpecification(AbSalesmanEntity.class,
                criterions);
        long count = salesmanDao.count(spec);
        return (int) count;
    }

    @Override
    public SalesmanInfo[] getSalesmans(Criterion[] criterions, int startPosition, int maxResults,
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
        Specification<AbSalesmanEntity> spec = CriteriaUtils.getSpecification(AbSalesmanEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<AbSalesmanEntity> list = salesmanDao.findList(spec, startPosition, maxResults, sort);
        List<SalesmanInfo> result = new ArrayList<SalesmanInfo>(list.size());
        for(AbSalesmanEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new SalesmanInfo[result.size()]);
    }
    @Override
    @Transactional
    public SalesmanInfo save(SalesmanInfo info) {
        AbSalesmanEntity entity = warp(info);
        salesmanDao.persist(entity);
        salesmanDao.flush();
        return warp(entity);
    }
    @Override
    public SalesmanInfo findById(Long id) {
        Optional<AbSalesmanEntity> entity = salesmanDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public SalesmanInfo update(SalesmanInfo info) {
        Optional<AbSalesmanEntity> entity = salesmanDao.findById(info.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(info);
            AbSalesmanEntity result = salesmanDao.save(e);
            salesmanDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removeSalesman(SalesmanInfo info) {
        salesmanDao.delete(warp(info));
        salesmanDao.flush();
    }
    @Transactional
    @Override
    public void removeSalesman(Long id) {
        salesmanDao.deleteById(id);
        salesmanDao.flush();
    }
    private SalesmanInfo warp(AbSalesmanEntity entity) {
        if(entity == null) {
            return null;
        }
        SalesmanInfo info = new SalesmanInfo();
        info.setId(entity.getId());
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setPwd(entity.getPwd());
        info.setIsentersys(entity.getIsentersys());
        info.setEntrydate(entity.getEntrydate());
        info.setResigndate(entity.getResigndate());
        info.setName(entity.getName());
        info.setRemarks(entity.getRemarks());
        info.setCreatedate(entity.getCreatedate());
        info.setModifydate(entity.getModifydate());
        return info;
    }
    private AbSalesmanEntity warp(SalesmanInfo info) {
        if(info == null) {
            return null;
        }
        AbSalesmanEntity entity = new AbSalesmanEntity();
        entity.setId(info.getId());
        entity.setCode(info.getCode());
        entity.setName(info.getName());
        entity.setPwd(info.getPwd());
        entity.setIsentersys(info.getIsentersys());
        entity.setEntrydate(info.getEntrydate());
        entity.setResigndate(info.getResigndate());
        entity.setName(info.getName());
        entity.setRemarks(info.getRemarks());
        entity.setCreatedate(info.getCreatedate());
        entity.setModifydate(info.getModifydate());
        return entity;
    }

    @Override
    public SalesmanInfo findByCode(String fundcode) {
        AbSalesmanEntity entity = salesmanDao.findByCode(fundcode);
        return warp(entity);
    }
}
