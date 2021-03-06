/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.mobile.rest.query;
/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月3日
*/

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "注册参数")
public class QueryRegisterInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1710710920930205544L;
    private String account;
    private String passwd;
    private String telephone;
    private String referrerAccount;
    @ApiModelProperty(value = "账户",required = true,allowEmptyValue = false,example="张三")
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    @ApiModelProperty(value = "密码",required = true,allowEmptyValue = false,example="123xy")
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    @ApiModelProperty(value = "联系电话",required = true,allowEmptyValue = false,example="123123123")
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @ApiModelProperty(value = "推荐人账户",example="")
    public String getReferrerAccount() {
        return referrerAccount;
    }
    public void setReferrerAccount(String referrerAccount) {
        this.referrerAccount = referrerAccount;
    }
    
    
}
