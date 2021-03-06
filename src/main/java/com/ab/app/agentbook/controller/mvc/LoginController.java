package com.ab.app.agentbook.controller.mvc;
///*
//* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
//* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
//terms.
//*
//*/
//package com.ab.app.agentbook.controller.mvc;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.ab.app.agentbook.rest.enums.ResultCode;
//import com.ab.app.agentbook.rest.query.Result;
//import com.ab.app.agentbook.security.user.info.User;
//import com.ab.app.agentbook.security.user.service.UserService;
//
//
///**
//* <p>
//* 
//技术文档
//版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
//* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
//* @version 1.0, 2020年10月21日
//*/
//@Controller("loginController")
//public class LoginController {
//    @Autowired
//    @Qualifier("userService")
//    private UserService userService;
//    
//    public UserService getUserService() {
//        return userService;
//    }
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//    @RequestMapping(path = "/login")
//    public String login() {
//        return "login";
//    }
//    @RequestMapping(path = "/main")
//    public String main() {
//        return "main";
//    }
//    
//    @RequestMapping(value="/sign", method = RequestMethod.POST)
//    @ResponseBody
//    public Result sign(@RequestParam ("account") String account,@RequestParam ("passwd") String passwd) {
//        Result result = new Result();
//        if(StringUtils.isBlank(account)) {
//            result.setCode(10002);
//            result.setMsg("账户为空，请重新输入！");
//            return result;
//        }
//        if(StringUtils.isBlank(passwd)) {
//            result.setCode(10002);
//            result.setMsg("密码为空，请重新输入！");
//            return result;
//        }
//        User olduser = userService.findUserByAccount(account);
//        if(olduser==null) {
//            return result.failure(ResultCode.USER_NOT_EXIST);
//        }
//        User user = userService.userLoginCheck(account, passwd);
//        if(user == null) {
//            return result.failure(ResultCode.USER_LOGIN_PASSWD_ERROR);
//        }
//        return result.success(user);
//    }
//}
