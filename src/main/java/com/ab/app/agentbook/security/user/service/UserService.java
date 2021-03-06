package com.ab.app.agentbook.security.user.service;

import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.security.user.entity.XyUserEntity;
import com.ab.app.agentbook.security.user.info.User;

public interface UserService {
    /**
     * 获取用户信息
     * @param account
     *          用户账户
     * @return 用户信息
     */
    public User findUserByAccount(String account);
    /**
     * 检查用户是否存在，如果存在则确认登陆，如果不存在则不允许登陆
     * @param account
     *          用户账户
     * @param password
     *          用户登录密码
     * @return true 允许登陆，false 不允许登陆
     */
    public User userLoginCheck(String account,String password);
    /**
     * 保存用户信息
     * @param user 
     *          用户信息
     * @return 用户信息
     */
    public User save(User user);
    /**
     * 根据ID获取用户信息
     * @param id
     *          用户主键ID
     * @return
     */
    public User findById(Long id);
    /**
     * 更新用户信息
     * @param warp
     * @return
     */
    public User update(User user);
    /**
     * 删除用户信息
     * @param user
     */
    public void removeUser(User user);
    /**
     * 根据用户ID删除用户信息
     * @param id
     */
    public void removeUser(Long id);
    /**
     * 根据查询条件查询数据
     * @param criterions
     * @return
     */
    public int getUserCount(Criterion[] criterions);
    public User[] getUsers(Criterion[] criterions, int startPosition, int maxResults, String orderBy);
	public User[] findByReferrerAccount(String account);
    XyUserEntity findByAccount(String account);
}
