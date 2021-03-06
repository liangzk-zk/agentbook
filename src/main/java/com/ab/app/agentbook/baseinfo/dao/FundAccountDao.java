package com.ab.app.agentbook.baseinfo.dao;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.baseinfo.entity.AbFundAccountEntity;
import com.ab.app.agentbook.jpa.GenericRepository;
@Repository
public interface FundAccountDao extends GenericRepository<AbFundAccountEntity, Long>{

    AbFundAccountEntity findByFundcode(String fundcode);
    
}
