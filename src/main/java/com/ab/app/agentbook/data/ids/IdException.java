package com.ab.app.agentbook.data.ids;

/**
 * 生成ID发生错误时所抛出的异常
 *
 */
public class IdException extends Exception {
	private static final long serialVersionUID = -8260632133325631349L;

	public IdException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdException(String message) {
		super(message);
	}

}
