package com.ab.app.agentbook.rest.query;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="充值参数")
public class RechargeQuery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1551329707520265924L;
	private Long paymentId;
	private Long userId;
	private Long rechargeAmount;
	@ApiModelProperty(value = "用户ID",example = "1")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@ApiModelProperty(value = "充值金额",example = "100")
	public Long getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Long rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	@ApiModelProperty(value = "凭证ID",example = "1")
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	
}
