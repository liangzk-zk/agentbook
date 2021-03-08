package com.ab.app.agentbook.baseinfo.info;
import java.io.Serializable;
/**
 * 付款项目
 * @author Administrator
 *
 */
public class PaymentInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3900112260858443574L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 付款项目编码
     */
    private String code;
    /**
     * 支出类型
     */
    private Long paytype;
    /**
     * 支出类型名称
     */
    private String paytypename;
    /**
     * 付款项目名称
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

    public Long getPaytype() {
        return paytype;
    }

    public void setPaytype(Long paytype) {
        this.paytype = paytype;
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

    public String getPaytypename() {
        return paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
    }
    
}
