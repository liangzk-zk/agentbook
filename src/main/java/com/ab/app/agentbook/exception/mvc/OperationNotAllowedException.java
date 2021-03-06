/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：不允许在指特定的资源上执行某种请求操作，比如在一个已经发布的资源执行更新请求操作。
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class OperationNotAllowedException extends NestedRuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = -4608472803690504994L;

    public OperationNotAllowedException(String msg) {
        super(msg);
    }

    public OperationNotAllowedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
