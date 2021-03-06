package com.ab.app.agentbook.company.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AB_COMPANY", schema = "agentbook", catalog = "")
public class XyCompanyEntity {
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
    @Id
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "COMPANYNAME", nullable = true, length = 32)
    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    @Basic
    @Column(name = "PAYEE", nullable = true, length = 50)
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Basic
    @Column(name = "BANKNAME", nullable = true, length = 157)
    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    @Basic
    @Column(name = "CARDID", nullable = true)
    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    @Basic
    @Column(name = "WECHATPAY", nullable = true, length = 300)
    public String getWechatpay() {
        return wechatpay;
    }

    public void setWechatpay(String wechatpay) {
        this.wechatpay = wechatpay;
    }

    @Basic
    @Column(name = "ALIPAY", nullable = true, length = 300)
    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }
    @Basic
    @Column(name = "TELEPHONE", nullable = true, length = 50)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "ENABLED", nullable = false)
    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Basic
    @Column(name = "SYNOPSIS", nullable = true, length = 300)
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XyCompanyEntity that = (XyCompanyEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(companyname, that.companyname) &&
                Objects.equals(payee, that.payee) &&
                Objects.equals(bankname, that.bankname) &&
                Objects.equals(cardid, that.cardid) &&
                Objects.equals(wechatpay, that.wechatpay) &&
                Objects.equals(alipay, that.alipay) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyname, payee, bankname, cardid, wechatpay, alipay, telephone, enabled, status);
    }
}
