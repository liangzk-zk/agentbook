///*
//* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
//* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
//terms.
//*
//*/
//package com.ab.app.agentbook.company.entity;
//
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
//* <p>
//* 
//技术文档
//版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
//* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
//* @version 1.0, 2020年11月12日
//*/
//@Entity
//@Table(name = "AB_COMPANY_CUSTOMER_INFO", schema = "agentbook", catalog = "")
//public class AbCustomerInfoEntity{
//    private Long id;
//    /**
//     * 客户编码
//     */
//    private String customerCode;
//    /**
//     * 客户名称
//     */
//    private String customerName;
//    //电话
//    private String tel;
//    /**
//     * 客户分类
//     */
//    private Long transactionId;
//    /**
//     * 手机
//     */
//    private String phone;
//    /**
//     * 客户名称
//     */
//    private String remarks;
//    /**
//     * 联系人
//     */
//    private String contactPerson;
//    /**
//     * 拿票地址
//     */
//    private String npAddress;
//    /**
//     * 自编码
//     */
//    private String selfEncoding;
//    /**
//     * 地址
//     */
//    private String address;
//    /**
//     * 备注一
//     */
//    private String remarks1;
//    /**
//     * 备注二
//     */
//    private String remarks2;
//    //企业信息
//    /**
//     * 企业信用代码
//     */
//    private String enterpriseCreditCode;
//    /**
//     * 企业报税密码
//     */
//    private String enterpriseTaxFilingpwd;
//    /**
//     * 企业报税密码
//     * @return
//     */
//    private String reportTax;
//    /**
//     * 法人姓名
//     * @return
//     */
//    
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Column(name = "ID", nullable = false)
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    @Basic
//    @Column(name = "PARENTID", nullable = true)
//    public Long getParentId() {
//        return parentId;
//    }
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//    @Basic
//    @Column(name = "CODE", nullable = true, length = 32)
//    public String getCode() {
//        return code;
//    }
//    public void setCode(String code) {
//        this.code = code;
//    }
//    @Basic
//    @Column(name = "NAME", nullable = true, length = 32)
//    public String getName() {
//        return name;
//    }
//    
//    public void setName(String name) {
//        this.name = name;
//    }
//    @Basic
//    @Column(name = "MNEMONIC_CODE", nullable = true, length = 32)
//    public String getMnemonicCode() {
//        return mnemonicCode;
//    }
//    public void setMnemonicCode(String mnemonicCode) {
//        this.mnemonicCode = mnemonicCode;
//    }
//    @Basic
//    @Column(name = "REMARKS", nullable = true, length = 32)
//    public String getRemarks() {
//        return remarks;
//    }
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }
//    
//}
