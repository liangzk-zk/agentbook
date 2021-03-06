package com.ab.app.agentbook.data.ids;

import java.io.Serializable;

/**
 * id修饰器，用于对生成的id叠加节点id等处理
 * <br>
 *
 */
public interface IdDecorator {

	/**
	 * 返回处理后的id
	 * 
	 * @param id
	 *            原始的id
	 * @return
	 */
	public Serializable getDecoratedId(Serializable id);
}
