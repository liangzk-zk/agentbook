package com.ab.app.agentbook.security.user.info;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="userInfo",description="用户信息")
public class UserInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8436934566078656355L;
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
    private Short enabled;
    private Integer weight;
    private String status;
    private Integer isrecharge=0;
    @ApiModelProperty(value = "id(如果是新增，则无需输入值！)",example="1")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @ApiModelProperty(value = "账号",required=true,allowEmptyValue=false,example="zhangsan")
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    @ApiModelProperty(value = "推荐人账号",allowEmptyValue=false,example="zhangsan")
    public String getReferrerAccount() {
        return referrerAccount;
    }
    public void setReferrerAccount(String referrerAccount) {
        this.referrerAccount = referrerAccount;
    }
    @ApiModelProperty(value = "登陆密码",required=true,allowEmptyValue=false,example="123xy")
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    @ApiModelProperty(value = "创建时间",example="123456")
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    @ApiModelProperty(value = "支付密码",allowEmptyValue=false,example="123xy")
    public String getPayPasswd() {
        return payPasswd;
    }
    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }
    @ApiModelProperty(value = "姓名",allowEmptyValue=false,example="张三")
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
    @ApiModelProperty(value = "昵称",allowEmptyValue=false,example="葬爱家族")
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @ApiModelProperty(value = "描述",allowEmptyValue=false,example="用户描述信息")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @ApiModelProperty(value = "手机号",required=true,allowEmptyValue=false,example="手机号")
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @ApiModelProperty(value = "是否启用",required=true,allowEmptyValue=false,example="0")
    public Short getEnabled() {
        return enabled;
    }
    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }
    @ApiModelProperty(value = "排序号",allowEmptyValue=false,example="0")
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    @ApiModelProperty(value = "状态",allowEmptyValue=false,example="是")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @ApiModelProperty(value = "是否充值过",allowEmptyValue=false,example="0")
    public Integer getIsrecharge() {
        return isrecharge;
    }
    public void setIsrecharge(Integer isrecharge) {
        this.isrecharge = isrecharge;
    }
    
}
