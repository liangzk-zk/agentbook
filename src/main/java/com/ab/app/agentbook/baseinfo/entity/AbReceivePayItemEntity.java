package com.ab.app.agentbook.baseinfo.entity;

import javax.persistence.*;
import java.util.Objects;
/**
 * 收款项目
 * @author Administrator
 *
 */
@Entity
@Table(name = "ab_receive_pay_item", schema = "agentbook", catalog = "")
public class AbReceivePayItemEntity {
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
    @Column(name = "RECEIVEPAYTYPE", nullable = false)
    public Long getReceivepaytype() {
        return receivepaytype;
    }

    public void setReceivepaytype(Long receivepaytype) {
        this.receivepaytype = receivepaytype;
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

    @Basic
    @Column(name = "ISCOMMISSION", nullable = true)
    public Short getIscommission() {
        return iscommission;
    }

    public void setIscommission(Short iscommission) {
        this.iscommission = iscommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbReceivePayItemEntity that = (AbReceivePayItemEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(receivepaytype, that.receivepaytype) &&
                Objects.equals(name, that.name) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(modifydate, that.modifydate) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(iscommission, that.iscommission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, receivepaytype, name, createdate, modifydate, remarks, iscommission);
    }
}
