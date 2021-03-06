package com.ab.app.agentbook.security.role.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.jpa.GenericRepository;
import com.ab.app.agentbook.security.role.entity.XyUserRoleEntity;
@Repository
public interface UserRoleDao extends GenericRepository<XyUserRoleEntity, Long> {

	List<XyUserRoleEntity> findByUserId(Long userId);
}
