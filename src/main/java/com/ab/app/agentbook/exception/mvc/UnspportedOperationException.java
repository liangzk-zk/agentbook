/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * Restful接口异常：对资源进行该操作不支持，请求数据格式错误或者该操作没有实现
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public class UnspportedOperationException extends NestedRuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1310565639781909349L;

    public UnspportedOperationException(String msg) {
        super(msg);
    }

    public UnspportedOperationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
