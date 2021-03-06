package com.ab.app.agentbook.security.user.entity;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ab.app.agentbook.security.role.entity.XyRoleEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AB_USER", schema = "agentbook", catalog = "")
public class XyUserEntity implements UserDetails{
    /**
     * 
     */
    private static final long serialVersionUID = 2778585234353523439L;
    private Long id;
    private String account;
    private String referrerAccount;
    private String passwd;
    private String payPasswd;
    private String name;
    private Integer level;
    private String levelName;
    private Long createTime;
    private String nickName;
    private String description;
    private String telephone;
    private Integer weight=0;
    private String status;
    private Integer isrecharge=0;
    private List<XyRoleEntity> roles;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //@GenericGenerator(name = "custom-id", strategy = "com.muyuer.springdemo.core.CustomIDGenerator")
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ACCOUNT", length = 255)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    @Basic
    @Column(name = "REFERRERACCOUNT", length = 255)
    public String getReferrerAccount() {
        return referrerAccount;
    }

    public void setReferrerAccount(String referrerAccount) {
        this.referrerAccount = referrerAccount;
    }

    @Basic
    @Column(name = "PASSWD", nullable = true, length = 157)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 50)
    public String getName() {
        return name;
    }
    @Basic
    @Column(name = "LEVEL")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    @Basic
    @Column(name = "LEVELNAME")
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "CREATETIME", nullable = true)
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "TELEPHONE", nullable = true, length = 50)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @Basic
    @Column(name = "WEIGHT", nullable = false)
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Basic
    @Column(name = "PAY_PASSWD", nullable = true, length = 10)
    public String getPayPasswd() {
        return payPasswd;
    }

    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }
    @Basic
    @Column(name = "NICK_NAME", nullable = true, length = 10)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Basic
    @Column(name = "ISRECHARGE", nullable = true)
    public Integer getIsrecharge() {
        return isrecharge;
    }

    public void setIsrecharge(Integer isrecharge) {
        this.isrecharge = isrecharge;
    }
    @Transient
    public List<XyRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<XyRoleEntity> roles) {
        this.roles = roles;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XyUserEntity that = (XyUserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(account, that.account) &&
                Objects.equals(passwd, that.passwd) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, passwd, name, description, telephone, weight, status);
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<XyRoleEntity> roles = this.getRoles();
        for (XyRoleEntity role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return auths;
    }

    @Transient
    @Override
    public String getPassword() {
        return this.passwd;
    }
    @Transient
    @Override
    public String getUsername() {
        return this.account;
    }
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}
