package com.ab.app.agentbook.baseinfo.dao;

import com.ab.app.agentbook.baseinfo.entity.AbSalesmanEntity;
import com.ab.app.agentbook.jpa.GenericRepository;

public interface SalesmanDao extends GenericRepository<AbSalesmanEntity, Long>{

    AbSalesmanEntity findByCode(String fundcode);
    
}
