package com.ab.app.agentbook.baseinfo.info;

import java.io.Serializable;

public class SalesmanInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7090723749906083161L;
    /**
     * ID
     */
    private Long id;
    /**
     * 业务员编码
     */
    private String code;
    /**
     * 业务员名称
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 是否进入系统
     */
    private Integer isentersys;
    /**
     * 入职时间
     */
    private Long entrydate;
    /**
     * 离职时间
     */
    private Long resigndate;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getIsentersys() {
        return isentersys;
    }

    public void setIsentersys(Integer isentersys) {
        this.isentersys = isentersys;
    }

    public Long getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Long entrydate) {
        this.entrydate = entrydate;
    }

    public Long getResigndate() {
        return resigndate;
    }

    public void setResigndate(Long resigndate) {
        this.resigndate = resigndate;
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
