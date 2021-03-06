/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.mobile.rest.mvc.login;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.mobile.rest.query.QueryRegisterInfo;
import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.Result;
import com.ab.app.agentbook.security.user.info.User;
import com.ab.app.agentbook.security.user.service.UserService;

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
* @version 1.0, 2020年11月2日
*/
@Api
@RestController("restLoginMobileController")
@RequestMapping("/rest/mobile/login")
public class LoginMobileController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    public UserService getUserService() {
        return userService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation(value = "登陆", notes = "登陆", tags = {"LOGIN"})
    @RequestMapping(value="/mobileSign", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户登陆成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用登陆用户API内部报错") })
    @ResponseBody
    public Result mobileSign(
    		@ApiParam(name = "account", value = "账户",example = "zhangsan", required = true) @RequestParam ("account") String account,
    		@ApiParam(name = "passwd", value = "密码",example = "123xy", required = true) @RequestParam ("passwd") String passwd) {
        Result result = new Result();
        if(StringUtils.isBlank(account)) {
            result.setCode(10002);
            result.setMsg("账户为空，请重新输入！");
            return result;
        }
        if(StringUtils.isBlank(passwd)) {
            result.setCode(10002);
            result.setMsg("密码为空，请重新输入！");
            return result;
        }
        User olduser = userService.findUserByAccount(account);
        if(olduser==null) {
            return result.failure(ResultCode.USER_NOT_EXIST);
        }
        User user = userService.userLoginCheck(account, passwd);
        if(user == null) {
            return result.failure(ResultCode.USER_LOGIN_PASSWD_ERROR);
        }
        return result.success(user);
    }
    @ApiOperation(value = "注册", notes = "注册", tags = {"LOGIN"})
    @RequestMapping(value="/mobileRegister", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户登陆成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用登陆用户API内部报错") })
    @ResponseBody
    public Result mobileRegister(@RequestBody QueryRegisterInfo info) {
        Result result = new Result();
        if(StringUtils.isBlank(info.getAccount())) {
            result.setCode(10002);
            result.setMsg("账户为空，请重新输入！");
            return result;
        }
        if(StringUtils.isBlank(info.getPasswd())) {
            result.setCode(10002);
            result.setMsg("密码为空，请重新输入！");
            return result;
        }
        if(StringUtils.isBlank(info.getTelephone())) {
            result.setCode(10002);
            result.setMsg("联系方式为空，请重新输入！");
            return result;
        }
        User olduser = userService.findUserByAccount(info.getAccount());
        if(olduser!=null) {
            return result.failure(ResultCode.USER_HAS_EXISTED);
        }
        User newuser = new User();
        if(!StringUtils.isBlank(info.getReferrerAccount())) {
        	User referreruser = userService.findUserByAccount(info.getReferrerAccount());
            if(referreruser==null) {
                return result.failure(ResultCode.USER_REFERRER_NOT_EXIST);
            }
            newuser.setReferrerAccount(info.getReferrerAccount());
        }
        newuser.setCreateTime(new DateTime().getMillis());
        newuser.setAccount(info.getAccount());
        newuser.setPasswd(info.getPasswd());
        newuser.setTelephone(info.getTelephone());
        User user = userService.save(newuser);
        if(user == null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return result.success(user);
    }
}
