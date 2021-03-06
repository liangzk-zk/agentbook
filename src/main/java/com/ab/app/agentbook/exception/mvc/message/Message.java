/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ab.app.agentbook.exception.mvc.message;

import java.io.Serializable;

/**
 * rest接口用于描述操作成功结果的包装类
 * 
 * @see https://github.com/adnan-kamili/swagger-response-template
 */
public class Message implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7613057911624115367L;
    private String message;

    public static Message success() {
        return new Message("success");
    }

    public Message() {
        super();
    }

    public Message(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
