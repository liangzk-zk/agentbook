package com.ab.app.agentbook.baseinfo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ab_fund_account", schema = "agentbook", catalog = "")
public class AbFundAccountEntity {
    //主键ID
    private Long id;
    /**
     * 资金编码
     */
    private String fundcode;
    /**
     * 资金名称
     */
    private String fundname;
    /**
     * 自编码
     */
    private String selfcode;
    /**
     * 期初金额
     */
    private BigDecimal intialamount;
    /**
     * 创建时间
     */
    private Long createdate;
    /**
     * 修改时间
     */
    private Long modifydate;
    /**
     * 备注
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
    @Column(name = "FUNDCODE", nullable = true,length = 255)
    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    @Basic
    @Column(name = "FUNDNAME", nullable = true, length = 255)
    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    @Basic
    @Column(name = "SELFCODE", nullable = true, length = 255)
    public String getSelfcode() {
        return selfcode;
    }

    public void setSelfcode(String selfcode) {
        this.selfcode = selfcode;
    }

    @Basic
    @Column(name = "INTIALAMOUNT", nullable = true, precision = 2)
    public BigDecimal getIntialamount() {
        return intialamount;
    }

    public void setIntialamount(BigDecimal intialamount) {
        this.intialamount = intialamount;
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
    @Column(name = "REMARKS", nullable = true, length = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbFundAccountEntity that = (AbFundAccountEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fundcode, that.fundcode) &&
                Objects.equals(fundname, that.fundname) &&
                Objects.equals(selfcode, that.selfcode) &&
                Objects.equals(intialamount, that.intialamount) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(modifydate, that.modifydate) &&
                Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fundcode, fundname, selfcode, intialamount, createdate, modifydate, remarks);
    }
}
