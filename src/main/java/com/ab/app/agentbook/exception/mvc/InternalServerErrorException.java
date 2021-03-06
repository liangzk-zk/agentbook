/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：发生系统内部异常
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class InternalServerErrorException extends NestedRuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = 3351931237741444267L;

    public InternalServerErrorException(String msg) {
        super(msg);
    }

    public InternalServerErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
