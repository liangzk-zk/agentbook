package com.ab.app.agentbook.baseinfo.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.baseinfo.entity.AbPaymentCategoryItemEntity;
import com.ab.app.agentbook.jpa.GenericRepository;

/**
 * 支出项目类别
 * @author Administrator
 *
 */
@Repository
public interface PaymentCategoryDao extends GenericRepository<AbPaymentCategoryItemEntity, Long>{

    AbPaymentCategoryItemEntity findByCode(String fundcode);

    List<AbPaymentCategoryItemEntity> findByParentid(Long id);
    
}
