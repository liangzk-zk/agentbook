package com.ab.app.agentbook.security.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ab.app.agentbook.jpa.GenericRepository;
import com.ab.app.agentbook.security.user.entity.XyUserEntity;

@Repository
public interface UserDao extends GenericRepository<XyUserEntity, Long> {
    XyUserEntity findByAccountAndPasswd(String account, String password);
    XyUserEntity findByAccount(String account);
	List<XyUserEntity> findByReferrerAccount(String account);
}
