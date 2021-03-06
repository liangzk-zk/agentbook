package com.ab.app.agentbook.baseinfo.info;


import java.io.Serializable;
import java.math.BigDecimal;

public class FundAccountInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1081418020247888205L;
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
     * 助记码
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    public String getSelfcode() {
        return selfcode;
    }

    public void setSelfcode(String selfcode) {
        this.selfcode = selfcode;
    }

    public BigDecimal getIntialamount() {
        return intialamount;
    }

    public void setIntialamount(BigDecimal intialamount) {
        this.intialamount = intialamount;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public Long getModifydate() {
        return modifydate;
    }

    public void setModifydate(Long modifydate) {
        this.modifydate = modifydate;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
