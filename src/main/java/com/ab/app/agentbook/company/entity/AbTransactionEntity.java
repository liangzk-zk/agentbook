/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.company.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月12日
*/
@Entity
@Table(name = "AB_COMPANY_TRANSACTION", schema = "agentbook", catalog = "")
public class AbTransactionEntity{
    private Long id;
    /**
     * 往来类别父级Id
     */
    private Long parentId;
    //往来类别编码
    private String code;
    /**
     * 往来类别名称
     */
    private String name;
    /**
     * 往来类别助记码
     */
    private String mnemonicCode;
    /**
     * 往来类别备注
     */
    private String remarks;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Basic
    @Column(name = "PARENTID", nullable = true)
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    @Basic
    @Column(name = "CODE", nullable = true, length = 32)
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Basic
    @Column(name = "NAME", nullable = true, length = 32)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "MNEMONIC_CODE", nullable = true, length = 32)
    public String getMnemonicCode() {
        return mnemonicCode;
    }
    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }
    @Basic
    @Column(name = "REMARKS", nullable = true, length = 32)
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
}
