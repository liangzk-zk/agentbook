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

import com.ab.app.agentbook.baseinfo.dao.IncomeCategoryDao;
import com.ab.app.agentbook.baseinfo.entity.AbIncomeCategoryItemEntity;
import com.ab.app.agentbook.baseinfo.info.IncomeCategoryInfo;
import com.ab.app.agentbook.baseinfo.service.IncomeCategoryService;
import com.ab.app.agentbook.company.entity.AbTransactionEntity;
import com.ab.app.agentbook.company.info.AbTransactionInfo;
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
public class IncomeCategoryServiceImpl implements IncomeCategoryService,InitializingBean{
    private IncomeCategoryDao incomeCategoryDao;

    public IncomeCategoryDao getIncomeCategoryDao() {
        return incomeCategoryDao;
    }

    public void setIncomeCategoryDao(IncomeCategoryDao incomeCategoryDao) {
        this.incomeCategoryDao = incomeCategoryDao;
    }
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(incomeCategoryDao, "fundAccountDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @Override
    public int getIncomeCategoryCount(Criterion[] criterions) {
        Specification<AbIncomeCategoryItemEntity> spec = CriteriaUtils.getSpecification(AbIncomeCategoryItemEntity.class,
                criterions);
        long count = incomeCategoryDao.count(spec);
        return (int) count;
    }

    @Override
    public IncomeCategoryInfo[] getIncomeCategorys(Criterion[] criterions, int startPosition, int maxResults,
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
        Specification<AbIncomeCategoryItemEntity> spec = CriteriaUtils.getSpecification(AbIncomeCategoryItemEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<AbIncomeCategoryItemEntity> list = incomeCategoryDao.findList(spec, startPosition, maxResults, sort);
        List<IncomeCategoryInfo> result = new ArrayList<IncomeCategoryInfo>(list.size());
        for(AbIncomeCategoryItemEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new IncomeCategoryInfo[result.size()]);
    }
    @Override
    @Transactional
    public IncomeCategoryInfo save(IncomeCategoryInfo info) {
        AbIncomeCategoryItemEntity entity = warp(info);
        incomeCategoryDao.persist(entity);
        incomeCategoryDao.flush();
        return warp(entity);
    }
    @Override
    public IncomeCategoryInfo findById(Long id) {
        Optional<AbIncomeCategoryItemEntity> entity = incomeCategoryDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public IncomeCategoryInfo update(IncomeCategoryInfo info) {
        Optional<AbIncomeCategoryItemEntity> entity = incomeCategoryDao.findById(info.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(info);
            AbIncomeCategoryItemEntity result = incomeCategoryDao.save(e);
            incomeCategoryDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removeIncomeCategory(IncomeCategoryInfo info) {
        incomeCategoryDao.delete(warp(info));
        incomeCategoryDao.flush();
    }
    @Transactional
    @Override
    public void removeIncomeCategory(Long id) {
        incomeCategoryDao.deleteById(id);
        incomeCategoryDao.flush();
    }
    private IncomeCategoryInfo warp(AbIncomeCategoryItemEntity entity) {
        if(entity == null) {
            return null;
        }
        IncomeCategoryInfo info = new IncomeCategoryInfo();
        info.setId(entity.getId());
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setParentid(entity.getParentid());
        info.setRemarks(entity.getRemarks());
        info.setSelfcode(entity.getSelfcode());
        info.setCreatedate(entity.getCreatedate());
        info.setModifydate(entity.getModifydate());
        return info;
    }
    private AbIncomeCategoryItemEntity warp(IncomeCategoryInfo info) {
        if(info == null) {
            return null;
        }
        AbIncomeCategoryItemEntity entity = new AbIncomeCategoryItemEntity();
        entity.setId(info.getId());
        entity.setCode(info.getCode());
        entity.setName(info.getName());
        entity.setParentid(info.getParentid());
        entity.setRemarks(info.getRemarks());
        entity.setSelfcode(info.getSelfcode());
        entity.setCreatedate(info.getCreatedate());
        entity.setModifydate(info.getModifydate());
        return entity;
    }

    @Override
    public IncomeCategoryInfo findByCode(String fundcode) {
        AbIncomeCategoryItemEntity entity = incomeCategoryDao.findByCode(fundcode);
        return warp(entity);
    }

    @Override
    public IncomeCategoryInfo[] findByParentId(Long id) {
        List<AbIncomeCategoryItemEntity> entitys = incomeCategoryDao.findByParentid(id);
        List<IncomeCategoryInfo> result = new ArrayList<IncomeCategoryInfo>();
        for(AbIncomeCategoryItemEntity entity : entitys) {
            result.add(warp(entity));
        }
        return result.toArray(new IncomeCategoryInfo[result.size()]);
    }
}
