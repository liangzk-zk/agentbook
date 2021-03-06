///*
//* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
//* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
//terms.
//*
//*/
//package com.ab.app.agentbook.boot.config;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//
//import com.ab.app.agentbook.controller.criteron.Operator;
//import com.ab.app.agentbook.data.crud.criteria.Criterion;
//import com.ab.app.agentbook.jpa.ws.Expression;
//import com.ab.app.agentbook.jpa.ws.ExpressionUtils;
//import com.ab.app.agentbook.level.info.LevelInfo;
//import com.ab.app.agentbook.level.service.LevelService;
//import com.ab.app.agentbook.recharge.info.UserRecharageInfo;
//import com.ab.app.agentbook.recharge.service.UserRecharageService;
//
///**
//* <p>
//* 
//技术文档
//版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
//* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
//* @version 1.0, 2020年11月9日
//*/
//@Component
//public class UserRechargeJob implements InitializingBean{
//    private Map<String, String> queryrecharageFieldMapping;
//    @Autowired
//    @Qualifier("userRecharageService")
//    private UserRecharageService userRecharageService;
//    @Autowired
//    @Qualifier("levelService")
//    private LevelService levelService;
//    
//    public LevelService getLevelService() {
//        return levelService;
//    }
//
//    public void setLevelService(LevelService levelService) {
//        this.levelService = levelService;
//    }
//
//    public UserRecharageService getUserRecharageService() {
//        return userRecharageService;
//    }
//
//    public void setUserRecharageService(UserRecharageService userRecharageService) {
//        this.userRecharageService = userRecharageService;
//    }
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // TODO Auto-generated method stub
//        Assert.notNull(userRecharageService, "userRecharageService is required!");
//        if(queryrecharageFieldMapping == null) {
//            queryrecharageFieldMapping = new HashMap<String, String>(); 
//            queryrecharageFieldMapping.put("quota", "quota");
//        }
//    }
//    @Scheduled(cron = "0 0 0 * * ?")
//    //@Scheduled(cron = "0 0/1 * * * ?")
//    public void execute() {
//        List<Expression> expressions = new ArrayList<Expression>();
//        Expression expression = new Expression("quota", Operator.GT, 0);
//        expressions.add(expression);
//        Criterion[] criterions = ExpressionUtils.getCriteria(expressions.toArray(new Expression[expressions.size()]));
//        UserRecharageInfo[] datas = userRecharageService.getUserRecharages(criterions, -1, -1, null);
//        for(UserRecharageInfo info : datas) {
//            LevelInfo level = levelService.findByLevel(info.getLevel());
//            Long quota = info.getQuota();
//            Long dividends = info.getDividends();
//            Long cashdividends = info.getCashdividends();
//            info.setQuota(quota-level.getReleaseQuota());
//            if(dividends==null) {
//            	info.setDividends(Long.parseLong(String.valueOf(level.getReleaseQuota())));
//            }else {
//            	info.setDividends(dividends+level.getReleaseQuota());
//            }
//            if(cashdividends==null) {
//            	info.setCashdividends(Long.parseLong(String.valueOf(level.getReleaseQuota())));
//            }else {
//            	info.setCashdividends(cashdividends+level.getReleaseQuota());
//            }
//            userRecharageService.update(info);
//        }
//    }
//}
