package com.ab.app.agentbook.data.core;
///*
//* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
//* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
//terms.
//*
//*/
//package com.ab.app.agentbook.data.core;
//
//import java.io.Serializable;
//
//import org.hibernate.MappingException;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.id.IdentityGenerator;
//
///**
//* <p>
//* 
//技术文档
//版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
//* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
//* @version 1.0, 2020年10月29日
//*/
//public class CustomIDGenerator extends IdentityGenerator{
//    @Override
//    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
//        Object id =  SnowflakeIdHelper.getId();
//        if (id != null) {
//            return (Serializable) id;
//        }
//        return super.generate(session, object);
//    }
//}
