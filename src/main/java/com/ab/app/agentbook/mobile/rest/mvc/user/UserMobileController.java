/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.mobile.rest.mvc.user;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.Result;
import com.ab.app.agentbook.security.user.info.User;
import com.ab.app.agentbook.security.user.info.UserInfo;
import com.ab.app.agentbook.security.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月21日
*/
@Api
@RestController("userMobileController")
@RequestMapping("/rest/mobile/user")
public class UserMobileController {
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public UserService getUserService() {
        return userService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation(value = "新增用户信息", notes = "新增用户", tags = {"USER"})
	@RequestMapping(value="/addMobileUser", method = RequestMethod.POST)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增用户成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用新增用户API内部报错") })
	@ResponseBody
	public Result addMobileUser(@RequestBody UserInfo userInfo) {
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
	        return result.failure(ResultCode.USER_HAS_EXISTED);
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
    @RequestMapping(value="/updateMobileUser", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新用户成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新用户API内部报错") })
    @ResponseBody
    public Result updateMobileUser(@RequestBody UserInfo userInfo) {
        Result result = new Result();
        if(StringUtils.isEmpty(userInfo.getId())) {
            result.setCode(10002);
            result.setMsg("主键为空，请重新输入！");
            return result;
        }
//        if(StringUtils.isEmpty(userInfo.getPasswd())) {
//            result.setCode(10002);
//            result.setMsg("登陆密码为空，请重新输入！");
//            return result;
//        }
//        if(StringUtils.isEmpty(userInfo.getTelephone())) {
//            result.setCode(10002);
//            result.setMsg("联系电话为空，请重新输入！");
//            return result;
//        }
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
	@RequestMapping(value="/getMobileUser/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result getMobileUser(
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
	@ApiOperation(value = "获取用户推荐账户信息", notes = "获取用户推荐账户信息", tags = {"USER"})
    @RequestMapping(value="/getMobileReferrerUser/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteMobileUser(
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
        User[] users = userService.findByReferrerAccount(user.getAccount());
        if(users.length==0) {
        	return result.failure(ResultCode.USER_REFERRER_IS_NULL);
        }
        return result.success(users);
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
        userInfo.setLevel(user.getLevel());
        userInfo.setLevelName(user.getLevelName());
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
        user.setLevel(userInfo.getLevel());
        user.setLevelName(userInfo.getLevelName());
        user.setCreateTime(new DateTime().getMillis());
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
