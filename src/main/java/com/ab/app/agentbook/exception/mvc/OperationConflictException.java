/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：操作冲突，多个操作同时操作同一个资源，引起冲突
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class OperationConflictException extends NestedRuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = -728736640380604919L;

    public OperationConflictException(String msg) {
        super(msg);
    }

    public OperationConflictException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
