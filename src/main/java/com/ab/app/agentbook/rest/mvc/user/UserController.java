/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.mvc.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.controller.criteron.Operator;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.jpa.ws.Expression;
import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.InfoQuery;
import com.ab.app.agentbook.rest.query.InfoQueryResult;
import com.ab.app.agentbook.rest.query.Result;
import com.ab.app.agentbook.security.user.info.User;
import com.ab.app.agentbook.security.user.info.UserInfo;
import com.ab.app.agentbook.security.user.service.UserService;
import com.ab.app.agentbook.util.CriterionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月21日
*/
@Api
@RestController("userController")
@RequestMapping("/rest/user")
public class UserController implements InitializingBean{
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	private Map<String, String> queryUserFieldMapping;
	public UserService getUserService() {
        return userService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userService, "userService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("account", "account");
            queryUserFieldMapping.put("referrerAccount", "referrerAccount");
            queryUserFieldMapping.put("name", "name");
            queryUserFieldMapping.put("nickName", "nickName");
            queryUserFieldMapping.put("telephone", "telephone");
        }
    }
    @ApiOperation(value = "新增用户信息", notes = "新增用户", tags = {"USER"})
	@RequestMapping(value="/addUserInfo", method = RequestMethod.POST)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增用户成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用新增用户API内部报错") })
	@ResponseBody
	public Result addUserInfo(@RequestBody UserInfo userInfo) {
	    Result result = new Result();
	    if(StringUtils.isEmpty(userInfo.getAccount())) {
	        result.setCode(10002);
	        result.setMsg("账户为空，请重新输入！");
	        return result;
	    }
	    if(StringUtils.isEmpty(userInfo.getPasswd())) {
	        result.setCode(10002);
            result.setMsg("登陆密码为空，请重新输入！");
            return result;
        }
	    if(StringUtils.isEmpty(userInfo.getTelephone())) {
	        result.setCode(10002);
            result.setMsg("联系电话为空，请重新输入！");
            return result;
        }
	    User olduser = userService.findUserByAccount(userInfo.getAccount());
	    if(olduser!=null) {
	        
	    }
	    if(!StringUtils.isEmpty(userInfo.getReferrerAccount())) {
	        User referreruser = userService.findUserByAccount(userInfo.getReferrerAccount());
	        if(referreruser==null) {
	            return result.failure(ResultCode.USER_REFERRER_NOT_EXISTED);
	        }
	    }
	    userInfo.setCreateTime(new DateTime().getMillis());
	    User user = userService.save(warp(userInfo));
	    if(user==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
	    }
		return result.success(warp(user));
	}
    @ApiOperation(value = "更新用户信息", notes = "更新用户", tags = {"USER"})
    @RequestMapping(value="/updateUser", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新用户成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新用户API内部报错") })
    @ResponseBody
    public Result updateUser(@RequestBody UserInfo userInfo) {
        Result result = new Result();
        if(StringUtils.isEmpty(userInfo.getAccount())) {
            result.setCode(10002);
            result.setMsg("账户为空，请重新输入！");
            return result;
        }
        if(StringUtils.isEmpty(userInfo.getPasswd())) {
            result.setCode(10002);
            result.setMsg("登陆密码为空，请重新输入！");
            return result;
        }
        if(StringUtils.isEmpty(userInfo.getTelephone())) {
            result.setCode(10002);
            result.setMsg("联系电话为空，请重新输入！");
            return result;
        }
        User user = userService.findById(userInfo.getId());
		if(user==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        user = userService.update(warp(userInfo));
        if(user==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return result.success(warp(user));
    }
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息", tags = {"USER"})
	@RequestMapping(value="/getUser/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result getUser(
	        @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
	    Result result = new Result();
	    if(id==null) {
	        result.setCode(10002);
	        result.setMsg("id不允许为空！");
	        return result;
	    }
		User user = userService.findById(id);
		if(user==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
		return result.success(warp(user));
	}
	@ApiOperation(value = "删除用户信息", notes = "删除用户信息", tags = {"USER"})
    @RequestMapping(value="/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteUser(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        User user = userService.findById(id);
        if(user==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        userService.removeUser(id);
        return result.success(warp(user));
    }
	@ApiOperation(value = "获取用户列表信息", notes = "获取用户列表信息", tags = {"USER"})
    @RequestMapping(value="/getUserList", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Result getUserList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<UserInfo> queryResult = new InfoQueryResult<UserInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = userService.getUserCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new UserInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createTime DESC";
        }
        User[] datas = userService.getUsers(criterions, startPosition,
                maxResults, orderBy);
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(User user:datas) {
            userInfos.add(warp(user));
        }
        queryResult.setDatas(userInfos.toArray(new UserInfo[userInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
	@ApiOperation(value = "获取推荐用户列表信息", notes = "获取推荐用户列表信息", tags = {"USER"})
    @RequestMapping(value="/getUserSubList", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserSubList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<UserInfo> queryResult = new InfoQueryResult<UserInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        if("id".equals(expressions[0].getName())) {
            User u = userService.findById(expressions[0].getLongValue());
            Expression ex = new Expression("referrerAccount", Operator.EQ, u.getReferrerAccount());
            expressions[0] = ex;
        }
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = userService.getUserCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new UserInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createTime DESC";
        }
        User[] datas = userService.getUsers(criterions, startPosition,
                maxResults, orderBy);
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(User user:datas) {
            userInfos.add(warp(user));
        }
        queryResult.setDatas(userInfos.toArray(new UserInfo[userInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    private UserInfo warp(User user) {
        if(user==null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setAccount(user.getAccount());
        userInfo.setReferrerAccount(user.getReferrerAccount());
        userInfo.setName(user.getName());
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setPasswd(user.getPasswd());
        userInfo.setTelephone(user.getTelephone());
        userInfo.setDescription(user.getDescription());
        userInfo.setEnabled(user.getEnabled());
        userInfo.setNickName(user.getNickName());
        userInfo.setPayPasswd(user.getPayPasswd());
        userInfo.setStatus(user.getStatus());
        return userInfo;
    }
    private User warp(UserInfo userInfo) {
        if(userInfo==null) {
            return null;
        }
        User user = new User();
        user.setId(userInfo.getId());
        user.setAccount(userInfo.getAccount());
        user.setReferrerAccount(userInfo.getReferrerAccount());
        user.setName(userInfo.getName());
        user.setCreateTime(userInfo.getCreateTime());
        user.setPasswd(userInfo.getPasswd());
        user.setTelephone(userInfo.getTelephone());
        user.setDescription(userInfo.getDescription());
        user.setEnabled(userInfo.getEnabled());
        user.setNickName(userInfo.getNickName());
        user.setPayPasswd(userInfo.getPayPasswd());
        user.setStatus(userInfo.getStatus());
        return user;
    }
}
