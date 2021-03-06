/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：没有权限执行该请求操作
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class OperationForbiddenException extends NestedRuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = 754420394825177026L;

    public OperationForbiddenException(String msg) {
        super(msg);
    }

    public OperationForbiddenException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
