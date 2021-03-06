/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.query;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月12日
*/
@ApiModel(value="信息查询条件定义")
public class InfoCompanyQuery implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2760858672592171574L;
    private Long id;
    private Long payType;
    private MultipartFile poster;
    @ApiModelProperty(value = "公司ID",example = "1")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @ApiModelProperty(value = "支付类型",example = "0")
    public Long getPayType() {
        return payType;
    }
    public void setPayType(Long payType) {
        this.payType = payType;
    }
    @ApiModelProperty(value = "文件流",example = "")
    public MultipartFile getPoster() {
        return poster;
    }
    public void setPoster(MultipartFile poster) {
        this.poster = poster;
    }
    
}
