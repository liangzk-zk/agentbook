package com.ab.app.agentbook.baseinfo.info;
import java.io.Serializable;
/**
 * 收款项目类别
 * @author Administrator
 *
 */
public class IncomeCategoryInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 3772983579637761109L;
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 收款项目类别父ID
     */
    private Long parentid;
    /**
     * 收款项目编码
     */
    private String code;
    /**
     * 收款项目名称
     */
    private String name;
    /**
     * 收款项目助记码
     */
    private String selfcode;
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

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelfcode() {
        return selfcode;
    }

    public void setSelfcode(String selfcode) {
        this.selfcode = selfcode;
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
