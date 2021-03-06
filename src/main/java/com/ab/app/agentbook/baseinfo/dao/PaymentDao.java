package com.ab.app.agentbook.baseinfo.dao;


import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.baseinfo.entity.AbPaymentItemEntity;
import com.ab.app.agentbook.jpa.GenericRepository;
/**
 * 付款项目
 * @author Administrator
 *
 */
@Repository
public interface PaymentDao extends GenericRepository<AbPaymentItemEntity, Long>{

    AbPaymentItemEntity findByCode(String fundcode);
    
}
