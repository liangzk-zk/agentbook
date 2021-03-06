package com.ab.app.agentbook.baseinfo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ab_salesman", schema = "agentbook", catalog = "")
public class AbSalesmanEntity {
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
    @Column(name = "CODE", nullable = false, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PWD", nullable = true, length = 255)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "ISENTERSYS", nullable = true)
    public Integer getIsentersys() {
        return isentersys;
    }

    public void setIsentersys(Integer isentersys) {
        this.isentersys = isentersys;
    }

    @Basic
    @Column(name = "ENTRYDATE", nullable = true)
    public Long getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Long entrydate) {
        this.entrydate = entrydate;
    }

    @Basic
    @Column(name = "RESIGNDATE", nullable = true)
    public Long getResigndate() {
        return resigndate;
    }

    public void setResigndate(Long resigndate) {
        this.resigndate = resigndate;
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
        AbSalesmanEntity that = (AbSalesmanEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(pwd, that.pwd) &&
                Objects.equals(isentersys, that.isentersys) &&
                Objects.equals(entrydate, that.entrydate) &&
                Objects.equals(resigndate, that.resigndate) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(modifydate, that.modifydate) &&
                Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, pwd, isentersys, entrydate, resigndate, createdate, modifydate, remarks);
    }
}
