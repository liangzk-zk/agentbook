///*
//* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
//* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
//terms.
//*
//*/
//package com.ab.app.agentbook.mobile.rest.mvc.company;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ab.app.agentbook.company.info.CompanyInfo;
//import com.ab.app.agentbook.company.service.CompanyService;
//import com.ab.app.agentbook.rest.enums.ResultCode;
//import com.ab.app.agentbook.rest.query.Result;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import springfox.documentation.annotations.ApiIgnore;
//
///**
//* <p>
//* 
//技术文档
//版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
//* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
//* @version 1.0, 2020年10月30日
//*/
//@Api
//@RestController("companyMobileController")
//@RequestMapping("/rest/mobile/company")
//public class CompanyMobileController {
//    @Autowired
//    @Qualifier(value="companyService")
//    private CompanyService companyService;
//    @ApiIgnore
//    @ApiOperation(value = "上传支付凭证信息", notes = "上传支付凭证信息", tags = {"COMPANY"})
//    @RequestMapping(value="/uploadMobilePaymentVoucher", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "新增微信支付成功"),
//            @ApiResponse(code = 415, message = "请求的参数不合法"),
//            @ApiResponse(code = 500, message = "调用微信支付API内部报错") })
//    @ResponseBody
//    public Result uploadMobilePaymentVoucher(
//            @ApiParam(name = "id", value = "银行卡信息ID",example = "1", required = true) @RequestParam ("id") Long id,
//            @ApiParam(name = "payType", value = "上传",example = "1", required = true) @RequestParam ("payType") Long payType,
//            @ApiParam(name = "poster", value = "银行卡信息ID",example = "1", required = true) @RequestParam ("poster") MultipartFile poster,
//            HttpServletRequest request) {
//        Result result = new Result();
//        if(id==null) {
//            result.setCode(10002);
//            result.setMsg("id为空，请重新输入！");
//            return result;
//        }
//        if(poster.isEmpty()) {
//            result.setCode(10002);
//            result.setMsg("二维码图片为空，请重新输入！");
//            return result;
//        }
//        if(payType==null) {
//            result.setCode(10002);
//            result.setMsg("二维码类型为空，请重新输入！");
//            return result;
//        }
//        CompanyInfo company = new CompanyInfo();
//        try {
//            company = companyService.updateCompayQRcode(request,id,payType,poster);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(company==null) {
//            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
//        }
//        return result.success(company);
//    }
//    @ApiOperation(value = "获取公司信息", notes = "获取公司信息", tags = {"COMPANY"})
//    @RequestMapping(value="/getCompany", method = RequestMethod.GET)
//    @ResponseBody
//    public Result getCompany(){
//        Result result = new Result();
////        if(id==null) {
////            result.setCode(10002);
////            result.setMsg("id不允许为空！");
////            return result;
////        }
//        CompanyInfo company = companyService.findByAll()[0];
//        if(company==null) {
//            return result.failure(ResultCode.RESULE_DATA_NONE);
//        }
//        return result.success(company);
//    }
//}
