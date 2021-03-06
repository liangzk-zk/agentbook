package com.ab.app.agentbook.baseinfo.dao;


import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.baseinfo.entity.AbGroupEntity;
import com.ab.app.agentbook.jpa.GenericRepository;

/**
 * 部门信息
 * @author Administrator
 *
 */
@Repository
public interface GroupDao extends GenericRepository<AbGroupEntity, Long>{
    
}
