package com.ab.app.agentbook.company.info;

import java.io.Serializable;
import java.util.Objects;

public class CompanyInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -4970658650636500311L;
    private Long id;
    private String companyname;
    private String payee;
    private String bankname;
    private Long cardid;
    private String wechatpay;
    private String alipay;
    private String telephone;
    private Short enabled;
    private String status;
    private String synopsis;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public String getWechatpay() {
        return wechatpay;
    }

    public void setWechatpay(String wechatpay) {
        this.wechatpay = wechatpay;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
}
