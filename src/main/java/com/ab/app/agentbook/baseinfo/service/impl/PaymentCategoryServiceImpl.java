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

import com.ab.app.agentbook.baseinfo.dao.PaymentCategoryDao;
import com.ab.app.agentbook.baseinfo.entity.AbIncomeCategoryItemEntity;
import com.ab.app.agentbook.baseinfo.entity.AbPaymentCategoryItemEntity;
import com.ab.app.agentbook.baseinfo.info.IncomeCategoryInfo;
import com.ab.app.agentbook.baseinfo.info.PaymentCategoryInfo;
import com.ab.app.agentbook.baseinfo.service.PaymentCategoryService;
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
public class PaymentCategoryServiceImpl implements PaymentCategoryService,InitializingBean{
    private PaymentCategoryDao paymentCategoryDao;

    public PaymentCategoryDao getPaymentCategoryDao() {
        return paymentCategoryDao;
    }

    public void setPaymentCategoryDao(PaymentCategoryDao paymentCategoryDao) {
        this.paymentCategoryDao = paymentCategoryDao;
    }
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(paymentCategoryDao, "fundAccountDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @Override
    public int getPaymentCategoryCount(Criterion[] criterions) {
        Specification<AbPaymentCategoryItemEntity> spec = CriteriaUtils.getSpecification(AbPaymentCategoryItemEntity.class,
                criterions);
        long count = paymentCategoryDao.count(spec);
        return (int) count;
    }

    @Override
    public PaymentCategoryInfo[] getPaymentCategorys(Criterion[] criterions, int startPosition, int maxResults,
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
        Specification<AbPaymentCategoryItemEntity> spec = CriteriaUtils.getSpecification(AbPaymentCategoryItemEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<AbPaymentCategoryItemEntity> list = paymentCategoryDao.findList(spec, startPosition, maxResults, sort);
        List<PaymentCategoryInfo> result = new ArrayList<PaymentCategoryInfo>(list.size());
        for(AbPaymentCategoryItemEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new PaymentCategoryInfo[result.size()]);
    }
    @Override
    @Transactional
    public PaymentCategoryInfo save(PaymentCategoryInfo info) {
        AbPaymentCategoryItemEntity entity = warp(info);
        paymentCategoryDao.persist(entity);
        paymentCategoryDao.flush();
        return warp(entity);
    }
    @Override
    public PaymentCategoryInfo findById(Long id) {
        Optional<AbPaymentCategoryItemEntity> entity = paymentCategoryDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public PaymentCategoryInfo update(PaymentCategoryInfo info) {
        Optional<AbPaymentCategoryItemEntity> entity = paymentCategoryDao.findById(info.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(info);
            AbPaymentCategoryItemEntity result = paymentCategoryDao.save(e);
            paymentCategoryDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removePaymentCategory(PaymentCategoryInfo info) {
        paymentCategoryDao.delete(warp(info));
        paymentCategoryDao.flush();
    }
    @Transactional
    @Override
    public void removePaymentCategory(Long id) {
        paymentCategoryDao.deleteById(id);
        paymentCategoryDao.flush();
    }
    private PaymentCategoryInfo warp(AbPaymentCategoryItemEntity entity) {
        if(entity == null) {
            return null;
        }
        PaymentCategoryInfo info = new PaymentCategoryInfo();
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
    private AbPaymentCategoryItemEntity warp(PaymentCategoryInfo info) {
        if(info == null) {
            return null;
        }
        AbPaymentCategoryItemEntity entity = new AbPaymentCategoryItemEntity();
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
    public PaymentCategoryInfo findByCode(String fundcode) {
        AbPaymentCategoryItemEntity entity = paymentCategoryDao.findByCode(fundcode);
        return warp(entity);
    }

    @Override
    public PaymentCategoryInfo[] findByParentId(Long id) {
        List<AbPaymentCategoryItemEntity> entitys = paymentCategoryDao.findByParentid(id);
        List<PaymentCategoryInfo> result = new ArrayList<PaymentCategoryInfo>();
        for(AbPaymentCategoryItemEntity entity : entitys) {
            result.add(warp(entity));
        }
        return result.toArray(new PaymentCategoryInfo[result.size()]);
    }
}
