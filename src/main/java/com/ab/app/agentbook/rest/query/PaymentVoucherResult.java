package com.ab.app.agentbook.rest.query;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="paymentVoucherInfo",description="用户上传凭证信息")
public class PaymentVoucherResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3245058910256846269L;
	private Long id;
	private Long userId;
	private String account;
	private String username;
	private Long createTime;
	private String creator;
	private String voucherURL;
	@ApiModelProperty(value = "id",example="1")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ApiModelProperty(value = "用户Id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@ApiModelProperty(value = "用户账号")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@ApiModelProperty(value = "用户名称")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@ApiModelProperty(value = "创建时间",example="1")
	public Long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@ApiModelProperty(value = "创建人",example="张三")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@ApiModelProperty(value = "上传凭证图片地址",example="xxxx")
	public String getVoucherURL() {
		return voucherURL;
	}
	public void setVoucherURL(String voucherURL) {
		this.voucherURL = voucherURL;
	}
}
