package com.ab.app.agentbook.jpa;

import java.io.Serializable;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;


/**
 * 包含QueryDsl支持的Repository定义
 * 
 *
 */
public interface QueryDslGenericRepository<T, ID extends Serializable> extends
		QuerydslPredicateExecutor<T>, GenericRepository<T, ID> {
}
