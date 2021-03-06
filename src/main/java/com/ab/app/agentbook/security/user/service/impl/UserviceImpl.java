package com.ab.app.agentbook.security.user.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.data.crud.criteria.OrderBy;
import com.ab.app.agentbook.jpa.data.crud.CriteriaUtils;
import com.ab.app.agentbook.security.role.dao.RoleDao;
import com.ab.app.agentbook.security.role.dao.UserRoleDao;
import com.ab.app.agentbook.security.role.entity.XyRoleEntity;
import com.ab.app.agentbook.security.role.entity.XyUserRoleEntity;
import com.ab.app.agentbook.security.user.dao.UserDao;
import com.ab.app.agentbook.security.user.entity.XyUserEntity;
import com.ab.app.agentbook.security.user.info.User;
import com.ab.app.agentbook.security.user.service.UserService;

public class UserviceImpl implements UserService,InitializingBean{
	private UserDao userDao;
    private RoleDao roleDao;
    private UserRoleDao userRoleDao;
    private Map<String, String> queryUserFieldMapping;
    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    

    public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public UserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userDao, "userDao is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("account", "account");
            queryUserFieldMapping.put("referrerAccount", "referrerAccount");
            queryUserFieldMapping.put("name", "name");
            queryUserFieldMapping.put("nickName", "nickName");
            queryUserFieldMapping.put("telephone", "telephone");
        }
    }
    @Override
    public User findUserByAccount(String account) {
        XyUserEntity entity = userDao.findByAccount(account);
        return warp(entity);
    }

	@Override
    public XyUserEntity findByAccount(String account) {
        XyUserEntity entity = userDao.findByAccount(account);
        List<XyRoleEntity> roles = getRoles(entity.getId());
        entity.setRoles(roles);
        return entity;
    }
	private List<XyRoleEntity> getRoles(Long userId) {
		List<XyUserRoleEntity> userRoles = userRoleDao.findByUserId(userId);
		List<XyRoleEntity> roles = new ArrayList<XyRoleEntity>();
		for(XyUserRoleEntity userRole:userRoles) {
			Optional<XyRoleEntity> role = roleDao.findById(userRole.getRoleId());
			if(role.get()!=null) {
				roles.add(role.get());
			}
		}
		return roles;
	}
    @Override
    public User userLoginCheck(String account, String password) {
        XyUserEntity entity = userDao.findByAccountAndPasswd(account,password);
        return entity == null?null:warp(entity);
    }

    @Override
    @Transactional
    public User save(User user) {
        XyUserEntity entity = warp(user);
        userDao.persist(warp(user));
        userDao.flush();
        return warp(entity);
    }
    private User warp(XyUserEntity entity) {
        if(entity == null) {
            return null;
        }
        User userInfo = new User();
        userInfo.setId(entity.getId());
        userInfo.setAccount(entity.getAccount());
        userInfo.setReferrerAccount(entity.getReferrerAccount());
        userInfo.setName(entity.getName());
        userInfo.setLevel(entity.getLevel());
        userInfo.setLevelName(entity.getLevelName());
        userInfo.setPasswd(entity.getPasswd());
        userInfo.setCreateTime(entity.getCreateTime());
        userInfo.setTelephone(entity.getTelephone());
        userInfo.setDescription(entity.getDescription());
        userInfo.setNickName(entity.getNickName());
        userInfo.setPayPasswd(entity.getPayPasswd());
        userInfo.setStatus(entity.getStatus());
        userInfo.setWeight(entity.getWeight());
        userInfo.setIsrecharge(entity.getIsrecharge());
        return userInfo;
    }
    private XyUserEntity warp(User user) {
        if(user == null) {
            return null;
        }
        XyUserEntity entity = new XyUserEntity();
        entity.setId(user.getId());
        entity.setAccount(user.getAccount());
        entity.setReferrerAccount(user.getReferrerAccount());
        entity.setPasswd(user.getPasswd());
        entity.setName(user.getName());
        entity.setLevel(user.getLevel());
        entity.setLevelName(user.getLevelName());
        entity.setCreateTime(user.getCreateTime());
        entity.setTelephone(user.getTelephone());
        entity.setDescription(user.getDescription());
        entity.setNickName(user.getNickName());
        entity.setPayPasswd(user.getPayPasswd());
        entity.setStatus(user.getStatus());
        entity.setWeight(user.getWeight()!=null?user.getWeight():0);
        entity.setIsrecharge(user.getIsrecharge());
        return entity;
    }

    @Override
    public User findById(Long id) {
        Optional<XyUserEntity> entity = userDao.findById(id);
        return entity.map(e -> warp(e)).orElse(null);
    }

    @Override
    public User update(User user) {
        Optional<XyUserEntity> entity = userDao.findById(user.getId());
        if(entity==null) {
            return null;
        }
        return entity.map(e -> {
            e = warp(user);
            XyUserEntity result = userDao.save(e);
            userDao.flush();
            return warp(result);
            }).orElse(null);
    }
    @Transactional
    @Override
    public void removeUser(User user) {
        userDao.delete(warp(user));
        userDao.flush();
    }
    @Transactional
    @Override
    public void removeUser(Long id) {
        userDao.deleteById(id);
        userDao.flush();
    }

    @Override
    public int getUserCount(Criterion[] criterions) {
        Specification<XyUserEntity> spec = CriteriaUtils.getSpecification(XyUserEntity.class,
                criterions);
        long count = userDao.count(spec);
        return (int) count;
    }

    @Override
    public User[] getUsers(Criterion[] criterions, int startPosition, int maxResults, String orderBy) {
        OrderBy[] orders = null;
        if (StringUtils.isNotBlank(orderBy)) {
            int pos = orderBy.indexOf(' ');
            String field = orderBy.substring(0, pos);
            if (queryUserFieldMapping != null && queryUserFieldMapping.containsKey(field)) {
                field = queryUserFieldMapping.get(field);
            }
            String dir = orderBy.substring(pos + 1);
            // 先按照流程优先级升序排列，然后按照传入的排序字段排序，
            // 最后按日期降序排（如果排序字段不是日期）
            if ("asc".equalsIgnoreCase(dir)) {
                orders = OrderBy.orderBy(OrderBy.asc(field));
            } else {
                orders = OrderBy.orderBy(OrderBy.desc(field));
            }
        }
        Specification<XyUserEntity> spec = CriteriaUtils.getSpecification(XyUserEntity.class,
                criterions);
        Sort sort = CriteriaUtils.getSort(orders);
        List<XyUserEntity> list = userDao.findList(spec, startPosition, maxResults, sort);
        List<User> result = new ArrayList<User>(list.size());
        for(XyUserEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new User[result.size()]);
    }

	@Override
	public User[] findByReferrerAccount(String account) {
		List<XyUserEntity> list = userDao.findByReferrerAccount(account);
		List<User> result = new ArrayList<User>(list.size());
		for(XyUserEntity entity:list) {
            result.add(warp(entity));
        }
        return result.toArray(new User[result.size()]);
	}
}
