package com.ab.app.agentbook.baseinfo.info;

import java.io.Serializable;

/**
 * 收款项目
 * @author Administrator
 *
 */
public class ReceivePayInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -5859872840743060043L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 收款项目编码
     */
    private String code;
    /**
     * 收款项目类别
     */
    private Long receivepaytype;
    /**
     * 收款项目类别名称
     */
    private String receivepayname;
    /**
     * 收款项目名称
     */
    private String name;
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
    /**
     * 是否计算提成
     */
    private Short iscommission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getReceivepaytype() {
        return receivepaytype;
    }

    public void setReceivepaytype(Long receivepaytype) {
        this.receivepaytype = receivepaytype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Short getIscommission() {
        return iscommission;
    }

    public void setIscommission(Short iscommission) {
        this.iscommission = iscommission;
    }

    public void setReceivepayname(String receivepayname) {
        this.receivepayname = receivepayname;
    }

    public String getReceivepayname() {
        return receivepayname;
    }
}
