/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：没找到指定的Rest资源
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class NotFoundResourceException extends NestedRuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = -3685015855936062877L;

    public NotFoundResourceException(String msg) {
        super(msg);
    }

    public NotFoundResourceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
