package com.ab.app.agentbook.security.user.info;

import java.io.Serializable;


public class User implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3846019053986891492L;
    private Long id;
    private String account;
    private String referrerAccount;
    private String passwd;
    private Long createTime;
    private String payPasswd;
    private String name;
    private Integer level;
    private String levelName;
    private String nickName;
    private String description;
    private String telephone;
    private Short enabled;
    private Integer weight;
    private String status;
    private Integer isrecharge=0;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getPayPasswd() {
        return payPasswd;
    }
    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }
    
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getLevelName() {
        return levelName;
    }
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Short getEnabled() {
        return enabled;
    }
    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReferrerAccount() {
        return referrerAccount;
    }
    public void setReferrerAccount(String referrerAccount) {
        this.referrerAccount = referrerAccount;
    }
    public Integer getIsrecharge() {
        return isrecharge;
    }
    public void setIsrecharge(Integer isrecharge) {
        this.isrecharge = isrecharge;
    }
    
}
