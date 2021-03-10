package com.ab.app.agentbook.company.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ab_company_customer_info", schema = "agentbook", catalog = "")
public class AbCompanyCustomerInfoEntity {
    private Long id;
    private Long createdate;
    private Long modifydate;
    private String customercode;
    private String customername;
    private String tel;
    private String transactionid;
    private String phone;
    private String contactperson;
    private Long npaddress;
    private String selfencoding;
    private String address;
    private String remarks1;
    private String remarks2;
    private String enpcreditcode;
    private String enptaxfilingpwd;
    private String legalname;
    private String legalphone;
    private String stampduty;
    private String legalcard;
    private String incometax;
    private String surtax;
    private String bank;
    private String naturetax;
    private String setupdate;
    private String bankcard;
    private String threeprotocol;
    private String enptype;
    private Short paperworknum;
    private Short shareholdernum;
    private String taxoffice;
    private String salesman;
    private String taxFilingdone;
    private String businessstartdate;
    private String accounting;
    private String isinvoiving;
    private Short taxtype;
    private Short earlycollection;

    @Id
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CREATEDATE", nullable = true)
    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE", nullable = true)
    public Long getModifydate() {
        return modifydate;
    }

    public void setModifydate(Long modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "CUSTOMERCODE", nullable = false, length = 255)
    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    @Basic
    @Column(name = "CUSTOMERNAME", nullable = false, length = 255)
    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    @Basic
    @Column(name = "TEL", nullable = true, length = 255)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "TRANSACTIONID", nullable = true, length = 157)
    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 157)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "CONTACTPERSON", nullable = true, length = 50)
    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    @Basic
    @Column(name = "NPADDRESS", nullable = true)
    public Long getNpaddress() {
        return npaddress;
    }

    public void setNpaddress(Long npaddress) {
        this.npaddress = npaddress;
    }

    @Basic
    @Column(name = "SELFENCODING", nullable = true, length = 50)
    public String getSelfencoding() {
        return selfencoding;
    }

    public void setSelfencoding(String selfencoding) {
        this.selfencoding = selfencoding;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "REMARKS1", nullable = true, length = 255)
    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    @Basic
    @Column(name = "REMARKS2", nullable = true, length = 255)
    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = remarks2;
    }

    @Basic
    @Column(name = "ENPCREDITCODE", nullable = true, length = 255)
    public String getEnpcreditcode() {
        return enpcreditcode;
    }

    public void setEnpcreditcode(String enpcreditcode) {
        this.enpcreditcode = enpcreditcode;
    }

    @Basic
    @Column(name = "ENPTAXFILINGPWD", nullable = true, length = 255)
    public String getEnptaxfilingpwd() {
        return enptaxfilingpwd;
    }

    public void setEnptaxfilingpwd(String enptaxfilingpwd) {
        this.enptaxfilingpwd = enptaxfilingpwd;
    }

    @Basic
    @Column(name = "LEGALNAME", nullable = true, length = 255)
    public String getLegalname() {
        return legalname;
    }

    public void setLegalname(String legalname) {
        this.legalname = legalname;
    }

    @Basic
    @Column(name = "LEGALPHONE", nullable = true, length = 255)
    public String getLegalphone() {
        return legalphone;
    }

    public void setLegalphone(String legalphone) {
        this.legalphone = legalphone;
    }

    @Basic
    @Column(name = "STAMPDUTY", nullable = true, length = 255)
    public String getStampduty() {
        return stampduty;
    }

    public void setStampduty(String stampduty) {
        this.stampduty = stampduty;
    }

    @Basic
    @Column(name = "LEGALCARD", nullable = true, length = 255)
    public String getLegalcard() {
        return legalcard;
    }

    public void setLegalcard(String legalcard) {
        this.legalcard = legalcard;
    }

    @Basic
    @Column(name = "INCOMETAX", nullable = true, length = 255)
    public String getIncometax() {
        return incometax;
    }

    public void setIncometax(String incometax) {
        this.incometax = incometax;
    }

    @Basic
    @Column(name = "SURTAX", nullable = true, length = 255)
    public String getSurtax() {
        return surtax;
    }

    public void setSurtax(String surtax) {
        this.surtax = surtax;
    }

    @Basic
    @Column(name = "BANK", nullable = true, length = 255)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "NATURETAX", nullable = true, length = 255)
    public String getNaturetax() {
        return naturetax;
    }

    public void setNaturetax(String naturetax) {
        this.naturetax = naturetax;
    }

    @Basic
    @Column(name = "SETUPDATE", nullable = true, length = 255)
    public String getSetupdate() {
        return setupdate;
    }

    public void setSetupdate(String setupdate) {
        this.setupdate = setupdate;
    }

    @Basic
    @Column(name = "BANKCARD", nullable = true, length = 255)
    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    @Basic
    @Column(name = "THREEPROTOCOL", nullable = true, length = 255)
    public String getThreeprotocol() {
        return threeprotocol;
    }

    public void setThreeprotocol(String threeprotocol) {
        this.threeprotocol = threeprotocol;
    }

    @Basic
    @Column(name = "ENPTYPE", nullable = true, length = 255)
    public String getEnptype() {
        return enptype;
    }

    public void setEnptype(String enptype) {
        this.enptype = enptype;
    }

    @Basic
    @Column(name = "PAPERWORKNUM", nullable = true)
    public Short getPaperworknum() {
        return paperworknum;
    }

    public void setPaperworknum(Short paperworknum) {
        this.paperworknum = paperworknum;
    }

    @Basic
    @Column(name = "SHAREHOLDERNUM", nullable = true)
    public Short getShareholdernum() {
        return shareholdernum;
    }

    public void setShareholdernum(Short shareholdernum) {
        this.shareholdernum = shareholdernum;
    }

    @Basic
    @Column(name = "TAXOFFICE", nullable = true, length = 255)
    public String getTaxoffice() {
        return taxoffice;
    }

    public void setTaxoffice(String taxoffice) {
        this.taxoffice = taxoffice;
    }

    @Basic
    @Column(name = "SALESMAN", nullable = true, length = 255)
    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    @Basic
    @Column(name = "TaxFILINGDONE", nullable = true, length = 255)
    public String getTaxFilingdone() {
        return taxFilingdone;
    }

    public void setTaxFilingdone(String taxFilingdone) {
        this.taxFilingdone = taxFilingdone;
    }

    @Basic
    @Column(name = "BUSINESSSTARTDATE", nullable = true, length = 255)
    public String getBusinessstartdate() {
        return businessstartdate;
    }

    public void setBusinessstartdate(String businessstartdate) {
        this.businessstartdate = businessstartdate;
    }

    @Basic
    @Column(name = "ACCOUNTING", nullable = true, length = 255)
    public String getAccounting() {
        return accounting;
    }

    public void setAccounting(String accounting) {
        this.accounting = accounting;
    }

    @Basic
    @Column(name = "ISINVOIVING", nullable = true, length = 255)
    public String getIsinvoiving() {
        return isinvoiving;
    }

    public void setIsinvoiving(String isinvoiving) {
        this.isinvoiving = isinvoiving;
    }

    @Basic
    @Column(name = "TAXTYPE", nullable = true)
    public Short getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(Short taxtype) {
        this.taxtype = taxtype;
    }

    @Basic
    @Column(name = "EARLYCOLLECTION", nullable = true)
    public Short getEarlycollection() {
        return earlycollection;
    }

    public void setEarlycollection(Short earlycollection) {
        this.earlycollection = earlycollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbCompanyCustomerInfoEntity that = (AbCompanyCustomerInfoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(modifydate, that.modifydate) &&
                Objects.equals(customercode, that.customercode) &&
                Objects.equals(customername, that.customername) &&
                Objects.equals(tel, that.tel) &&
                Objects.equals(transactionid, that.transactionid) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(contactperson, that.contactperson) &&
                Objects.equals(npaddress, that.npaddress) &&
                Objects.equals(selfencoding, that.selfencoding) &&
                Objects.equals(address, that.address) &&
                Objects.equals(remarks1, that.remarks1) &&
                Objects.equals(remarks2, that.remarks2) &&
                Objects.equals(enpcreditcode, that.enpcreditcode) &&
                Objects.equals(enptaxfilingpwd, that.enptaxfilingpwd) &&
                Objects.equals(legalname, that.legalname) &&
                Objects.equals(legalphone, that.legalphone) &&
                Objects.equals(stampduty, that.stampduty) &&
                Objects.equals(legalcard, that.legalcard) &&
                Objects.equals(incometax, that.incometax) &&
                Objects.equals(surtax, that.surtax) &&
                Objects.equals(bank, that.bank) &&
                Objects.equals(naturetax, that.naturetax) &&
                Objects.equals(setupdate, that.setupdate) &&
                Objects.equals(bankcard, that.bankcard) &&
                Objects.equals(threeprotocol, that.threeprotocol) &&
                Objects.equals(enptype, that.enptype) &&
                Objects.equals(paperworknum, that.paperworknum) &&
                Objects.equals(shareholdernum, that.shareholdernum) &&
                Objects.equals(taxoffice, that.taxoffice) &&
                Objects.equals(salesman, that.salesman) &&
                Objects.equals(taxFilingdone, that.taxFilingdone) &&
                Objects.equals(businessstartdate, that.businessstartdate) &&
                Objects.equals(accounting, that.accounting) &&
                Objects.equals(isinvoiving, that.isinvoiving) &&
                Objects.equals(taxtype, that.taxtype) &&
                Objects.equals(earlycollection, that.earlycollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, customercode, customername, tel, transactionid, phone, contactperson, npaddress, selfencoding, address, remarks1, remarks2, enpcreditcode, enptaxfilingpwd, legalname, legalphone, stampduty, legalcard, incometax, surtax, bank, naturetax, setupdate, bankcard, threeprotocol, enptype, paperworknum, shareholdernum, taxoffice, salesman, taxFilingdone, businessstartdate, accounting, isinvoiving, taxtype, earlycollection);
    }
}
