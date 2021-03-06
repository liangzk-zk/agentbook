package com.ab.app.agentbook.jpa;

import static org.springframework.data.querydsl.QuerydslUtils.QUERY_DSL_PRESENT;

import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * 用于建立{@link GenericRepository}的工厂类
 * 
 *
 */
public class GenericRepositoryFactory extends JpaRepositoryFactory {

	public GenericRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
			return QueryDslGenericRepositoryImpl.class;
		} else {
			return GenericRepositoryImpl.class;
		}
	}

    /**
     * Returns whether the given repository interface requires a QueryDsl specific implementation to be chosen.
     * 
     * @param repositoryInterface
     * @return
     */
    private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
        return QUERY_DSL_PRESENT && QuerydslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }

}
