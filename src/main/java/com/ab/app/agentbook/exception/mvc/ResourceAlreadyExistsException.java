/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：创建指定的Rest资源失败，该Rest资源已存在
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class ResourceAlreadyExistsException extends NestedRuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = 8721973119072971150L;

    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }

    public ResourceAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
