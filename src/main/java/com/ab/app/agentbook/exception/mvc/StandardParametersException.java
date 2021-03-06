/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import org.springframework.core.NestedRuntimeException;

/**
 * <p>
 * Restful接口异常：参数格式不正确
 * @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
 * @version 1.0, 2019年9月3日
 */
public class StandardParametersException extends NestedRuntimeException{
    /**
     * 
     */
    private static final long serialVersionUID = -3967132116428489559L;
    public StandardParametersException(String msg) {
        super(msg);
    }
    public StandardParametersException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
