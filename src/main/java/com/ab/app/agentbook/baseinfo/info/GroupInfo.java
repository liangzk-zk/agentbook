package com.ab.app.agentbook.baseinfo.info;

import javax.persistence.*;
import java.util.Objects;
/**
 * 部门信息
 * @author Administrator
 *
 */
public class GroupInfo {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 部门编码
     */
    private String code;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门助记码
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
