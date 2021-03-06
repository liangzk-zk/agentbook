package com.ab.app.agentbook.baseinfo.entity;

import javax.persistence.*;
import java.util.Objects;
/**
 * 部门信息
 * @author Administrator
 *
 */
@Entity
@Table(name = "ab_group", schema = "agentbook", catalog = "")
public class AbGroupEntity {
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
    @Column(name = "SELFCODE", nullable = true, length = 255)
    public String getSelfcode() {
        return selfcode;
    }

    public void setSelfcode(String selfcode) {
        this.selfcode = selfcode;
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
        AbGroupEntity that = (AbGroupEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(selfcode, that.selfcode) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(modifydate, that.modifydate) &&
                Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, selfcode, createdate, modifydate, remarks);
    }
}
