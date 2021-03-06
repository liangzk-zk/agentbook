/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ab.app.agentbook.exception.mvc.message;

import java.io.Serializable;

/**
 * rest接口用于描述错误的包装类
 * 
 * @see https://github.com/adnan-kamili/swagger-response-template
 */
public class Error implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -706852380618813151L;
    private int code;
    private String message;
    private String field;
    
    public Error() {
        super();
    }

    public Error(String message) {
        super();
        this.message = message;
    }

    
    public Error(int code, String message, String field) {
        super();
        this.code = code;
        this.message = message;
        this.field = field;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
