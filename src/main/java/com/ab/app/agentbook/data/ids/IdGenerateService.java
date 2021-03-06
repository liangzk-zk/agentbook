package com.ab.app.agentbook.data.ids;

/**
 * id生成服务
 * <br>
 *
 */
public interface IdGenerateService {

	/**
	 * 生成新的id
	 * 
	 * @param idName
	 *            id的名字
	 * @return
	 */
	public long getNextLongId(String idName);
}
