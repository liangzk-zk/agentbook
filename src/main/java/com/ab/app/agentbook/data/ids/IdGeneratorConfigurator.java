package com.ab.app.agentbook.data.ids;

/**
 * id生成器配置器，用于向扩展的hiberanate id生成器提供扩展配置参数<br>
 *
 */
public class IdGeneratorConfigurator {
	
	private int incrementSize;
	private String optimizer;
	private boolean preferLoOptimizer = true;
	private IdDecorator idDecoraror;

	/**
	 * 返回生成主键值时的递增量，如果值小于1则表示使用实体定义的原始定义值
	 * @return
	 */
	public int getIncrementSize() {
		return incrementSize;
	}

	/**
	 * 设置生成主键值时的递增量，如果值小于1则表示使用实体定义的原始定义值
	 * @param incrementSize
	 */
	public void setIncrementSize(int incrementSize) {
		this.incrementSize = incrementSize;
	}

	/**
	 * 返回生成主键时所使用的优化器
	 * @return
	 * @see org.hibernate.id.enhanced.TableGenerator#OPT_PARAM
	 */
	public String getOptimizer() {
		return optimizer;
	}
	
	/**
	 * 设置生成主键时所使用的优化器
	 * @param optimizer
	 * @see org.hibernate.id.enhanced.TableGenerator#OPT_PARAM
	 */
	public void setOptimizer(String optimizer) {
		this.optimizer = optimizer;
	}
	
	/**
	 * 返回使用缺省的主键生成优化器时是否将数据表中的值作为最小的可用id，缺省为true
	 * @return
	 */
	public boolean isPreferLoOptimizer() {
		return preferLoOptimizer;
	}
	
	/**
	 * 设置使用缺省的主键生成优化器时是否将数据表中的值作为最小的可用id，缺省为true
	 * @param preferLoOptimizer
	 */
	public void setPreferLoOptimizer(boolean preferLoOptimizer) {
		this.preferLoOptimizer = preferLoOptimizer;
	}
	
	/**
	 * 返回用于对生成的id进行修饰的修饰器
	 * @return
	 */
	public IdDecorator getIdDecoraror() {
		return idDecoraror;
	}

	/**
	 * 设置用于对生成的id进行修饰的修饰器
	 * @param idDecoraror
	 */
	public void setIdDecoraror(IdDecorator idDecoraror) {
		this.idDecoraror = idDecoraror;
	}
	
}
