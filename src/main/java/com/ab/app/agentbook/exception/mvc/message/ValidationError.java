/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ab.app.agentbook.exception.mvc.message;

import java.io.Serializable;

/**
 * rest接口用于描述错误信息的包装类
 * 
 * @see https://github.com/adnan-kamili/swagger-response-template
 */
public class ValidationError implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2670831817413202470L;
    private String message;
    private Error[] errors;

    public ValidationError() {
        super();
    }

    public ValidationError(String message) {
        super();
        this.message = message;
    }

    public ValidationError(String message, Error[] errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error[] getErrors() {
        return errors;
    }

    public void setErrors(Error[] errors) {
        this.errors = errors;
    }
}
