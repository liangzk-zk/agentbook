package com.ab.app.agentbook.baseinfo.dao;


import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.baseinfo.entity.AbReceivePayItemEntity;
import com.ab.app.agentbook.jpa.GenericRepository;

/**
 * 收款项目
 * @author Administrator
 *
 */
@Repository
public interface ReceivePayDao extends GenericRepository<AbReceivePayItemEntity, Long>{

    AbReceivePayItemEntity findByCode(String fundcode);
    
}
