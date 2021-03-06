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
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ab.app.agentbook.baseinfo.dao.FundAccountDao;
import com.ab.app.agentbook.baseinfo.entity.AbFundAccountEntity;
import com.ab.app.agentbook.baseinfo.info.FundAccountInfo;
import com.ab.app.agentbook.baseinfo.service.FundAccountService;
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
public class FundAccountServiceImpl implements FundAccountService,InitializingBean {
    private FundAccountDao fundAccountDao;

    public FundAccountDao getFundAccountDao() {
        return fundAccountDao;
    }

    public void setFundAccountDao(FundAccountDao fundAccountDao) {
        this.fundAccountDao = fundAccountDao;
    }
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(fundAccountDao, "fundAccountDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("fundcode", "fundcode");
            queryUserFieldMapping.put("fundname", "fundname");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @Override
    public int getFundAccountCount(Criterion[] criterions) {
        Specification<AbFundAccountEntity> spec = CriteriaUtils.getSpecification(AbFundAccountEntity.class,
                criterions);
        long count = fundAccountDao.count(spec);
        return (int) count;
    }

    @Override
    public FundAccountInfo[] getFundAccounts(Criterion[] criterions, int startPosition, int maxResults,
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
        Specification<AbFundAccountEntity> spec = CriteriaUtils.getSpecification(AbFundAccountEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<AbFundAccountEntity> list = fundAccountDao.findList(spec, startPosition, maxResults, sort);
        List<FundAccountInfo> result = new ArrayList<FundAccountInfo>(list.size());
        for(AbFundAccountEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new FundAccountInfo[result.size()]);
    }
    @Override
    @Transactional
    public FundAccountInfo save(FundAccountInfo info) {
        AbFundAccountEntity entity = warp(info);
        fundAccountDao.persist(entity);
        fundAccountDao.flush();
        return warp(entity);
    }
    @Override
    public FundAccountInfo findById(Long id) {
        Optional<AbFundAccountEntity> entity = fundAccountDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public FundAccountInfo update(FundAccountInfo info) {
        Optional<AbFundAccountEntity> entity = fundAccountDao.findById(info.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(info);
            AbFundAccountEntity result = fundAccountDao.save(e);
            fundAccountDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removeFundAccount(FundAccountInfo info) {
        fundAccountDao.delete(warp(info));
        fundAccountDao.flush();
    }
    @Transactional
    @Override
    public void removeFundAccount(Long id) {
        fundAccountDao.deleteById(id);
        fundAccountDao.flush();
    }
    private FundAccountInfo warp(AbFundAccountEntity entity) {
        if(entity == null) {
            return null;
        }
        FundAccountInfo info = new FundAccountInfo();
        info.setId(entity.getId());
        info.setFundcode(entity.getFundcode());
        info.setFundname(entity.getFundname());
        info.setIntialamount(entity.getIntialamount());
        info.setRemarks(entity.getRemarks());
        info.setSelfcode(entity.getSelfcode());
        info.setCreatedate(entity.getCreatedate());
        info.setModifydate(entity.getModifydate());
        return info;
    }
    private AbFundAccountEntity warp(FundAccountInfo info) {
        if(info == null) {
            return null;
        }
        AbFundAccountEntity entity = new AbFundAccountEntity();
        entity.setId(info.getId());
        entity.setFundcode(info.getFundcode());
        entity.setFundname(info.getFundname());
        entity.setIntialamount(info.getIntialamount());
        entity.setRemarks(info.getRemarks());
        entity.setSelfcode(info.getSelfcode());
        entity.setCreatedate(new DateTime().getMillis());
        entity.setModifydate(info.getModifydate());
        return entity;
    }

    @Override
    public FundAccountInfo findByFundCode(String fundcode) {
        AbFundAccountEntity entity = fundAccountDao.findByFundcode(fundcode);
        return warp(entity);
    }
}
