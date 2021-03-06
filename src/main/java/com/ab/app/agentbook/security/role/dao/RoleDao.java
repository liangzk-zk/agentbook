package com.ab.app.agentbook.security.role.dao;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.jpa.GenericRepository;
import com.ab.app.agentbook.security.role.entity.XyRoleEntity;
@Repository
public interface RoleDao extends GenericRepository<XyRoleEntity, Long> {
}
