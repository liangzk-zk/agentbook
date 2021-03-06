package com.ab.app.agentbook.baseinfo.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.baseinfo.entity.AbIncomeCategoryItemEntity;
import com.ab.app.agentbook.jpa.GenericRepository;

/**
 * 收款项目类别
 * @author Administrator
 *
 */
@Repository
public interface IncomeCategoryDao extends GenericRepository<AbIncomeCategoryItemEntity, Long>{
    AbIncomeCategoryItemEntity findByCode(String fundcode);
    List<AbIncomeCategoryItemEntity> findByParentid(Long id);
}
