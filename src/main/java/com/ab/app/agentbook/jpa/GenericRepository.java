package com.ab.app.agentbook.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 通用Repository定义，在spring{@link JpaRepository}基础上添加扩展功能
 * 
 *
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends
		JpaSpecificationExecutor<T>, JpaRepository<T, ID> {

	/**
	 * 返回满足指定约束的记录集合
	 * 
	 * @param spec
	 *            约束条件
	 * @param startPosition
	 *            返回结果前跳过的记录数
	 * @param maxResults
	 *            返回的最大记录条数，如果设置值小于1则返回所有结果
	 * @param sort
	 *            返回结果时的排序设置
	 * @return
	 */
	public List<T> findList(Specification<T> spec, int startPosition,
			int maxResults, Sort sort);

	/**
	 * 将数据对象插入到数据库中
	 * 
	 * @param entity
	 */
	public void persist(T entity);
	
	/**
	 * 将数据对象插入到数据库中并执行flush操作
	 * @param entity
	 */
	public void persistAndFlush(T entity);
	
	/**
	 * 将数据对象集合插入到数据库中
	 * @param entities
	 */
	public void persist(Iterable<T> entities);
	
	/**
	 * 将数据对象的修改保存到数据库中
	 * 
	 * @param entity
	 * @return
	 */
	public T merge(T entity);
	
	/**
	 * 将数据对象的修改保存到数据库中并执行flush操作
	 * 
	 * @param entity
	 * @return
	 */
	public T mergeAndFlush(T entity);

	/**
	 * 将数据对象集合保存到数据库中
	 * @param entities
	 * @return
	 */
	public Iterable<T> merge(Iterable<T> entities);
}
