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

import com.ab.app.agentbook.baseinfo.dao.PaymentDao;
import com.ab.app.agentbook.baseinfo.entity.AbPaymentItemEntity;
import com.ab.app.agentbook.baseinfo.info.PaymentInfo;
import com.ab.app.agentbook.baseinfo.service.PaymentService;
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
public class PaymentServiceImpl implements PaymentService,InitializingBean{
    private PaymentDao paymentDao;

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(paymentDao, "fundAccountDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
        }
    }
    @Override
    public int getPaymentCount(Criterion[] criterions) {
        Specification<AbPaymentItemEntity> spec = CriteriaUtils.getSpecification(AbPaymentItemEntity.class,
                criterions);
        long count = paymentDao.count(spec);
        return (int) count;
    }

    @Override
    public PaymentInfo[] getPayments(Criterion[] criterions, int startPosition, int maxResults,
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
        Specification<AbPaymentItemEntity> spec = CriteriaUtils.getSpecification(AbPaymentItemEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<AbPaymentItemEntity> list = paymentDao.findList(spec, startPosition, maxResults, sort);
        List<PaymentInfo> result = new ArrayList<PaymentInfo>(list.size());
        for(AbPaymentItemEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new PaymentInfo[result.size()]);
    }
    @Override
    @Transactional
    public PaymentInfo save(PaymentInfo info) {
        AbPaymentItemEntity entity = warp(info);
        paymentDao.persist(entity);
        paymentDao.flush();
        return warp(entity);
    }
    @Override
    public PaymentInfo findById(Long id) {
        Optional<AbPaymentItemEntity> entity = paymentDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public PaymentInfo update(PaymentInfo info) {
        Optional<AbPaymentItemEntity> entity = paymentDao.findById(info.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(info);
            AbPaymentItemEntity result = paymentDao.save(e);
            paymentDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removePayment(PaymentInfo info) {
        paymentDao.delete(warp(info));
        paymentDao.flush();
    }
    @Transactional
    @Override
    public void removePayment(Long id) {
        paymentDao.deleteById(id);
        paymentDao.flush();
    }
    private PaymentInfo warp(AbPaymentItemEntity entity) {
        if(entity == null) {
            return null;
        }
        PaymentInfo info = new PaymentInfo();
        info.setId(entity.getId());
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setRemarks(entity.getRemarks());
        info.setPaytype(entity.getPaytype());
        info.setCreatedate(entity.getCreatedate());
        info.setModifydate(entity.getModifydate());
        return info;
    }
    private AbPaymentItemEntity warp(PaymentInfo info) {
        if(info == null) {
            return null;
        }
        AbPaymentItemEntity entity = new AbPaymentItemEntity();
        entity.setId(info.getId());
        entity.setCode(info.getCode());
        entity.setName(info.getName());
        entity.setRemarks(info.getRemarks());
        entity.setPaytype(info.getPaytype());
        entity.setCreatedate(info.getCreatedate());
        entity.setModifydate(info.getModifydate());
        return entity;
    }

    @Override
    public PaymentInfo findByCode(String fundcode) {
        AbPaymentItemEntity entity = paymentDao.findByCode(fundcode);
        return warp(entity);
    }
}
