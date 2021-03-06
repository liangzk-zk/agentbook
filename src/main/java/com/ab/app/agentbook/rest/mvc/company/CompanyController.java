///*
//* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
//* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
//terms.
//*
//*/
//package com.ab.app.agentbook.rest.mvc.company;
//
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.RequestBody;
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
//import com.ab.app.agentbook.rest.query.InfoQuery;
//import com.ab.app.agentbook.rest.query.InfoQueryResult;
//import com.ab.app.agentbook.rest.query.Result;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import jodd.util.StringUtil;
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
//@RestController("companyController")
//@RequestMapping("/rest/company")
//public class CompanyController {
//    @Autowired
//    @Qualifier(value="companyService")
//    private CompanyService companyService;
//    @ApiOperation(value = "新增微信/支付宝支付信息", notes = "新增微信/支付宝支付信息", tags = {"COMPANY"})
//    @RequestMapping(value="/updateCpmpayPayQRcode", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "新增微信支付成功"),
//            @ApiResponse(code = 415, message = "请求的参数不合法"),
//            @ApiResponse(code = 500, message = "调用微信支付API内部报错") })
//    @ResponseBody
//    public Result updateCpmpayWeChatPay(
//            @RequestParam ("id") Long id,
//            @RequestParam ("payType") Long payType,
//            @RequestParam ("poster") MultipartFile poster,
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
//    @ApiOperation(value = "更新公司信息", notes = "更新公司上传凭证", tags = {"COMPANY"})
//    @RequestMapping(value="/updateCompany", method = RequestMethod.PUT)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "更新等级成功"),
//            @ApiResponse(code = 415, message = "请求的参数不合法"),
//            @ApiResponse(code = 500, message = "调用更新等级API内部报错") })
//    @ResponseBody
//    public Result updateCompany(@RequestBody CompanyInfo companyInfo) {
//        Result result = new Result();
//        if(StringUtil.isBlank(companyInfo.getBankname())) {
//            result.setResultCode(ResultCode.COMPANY_BANKNAME_IS_NULL);
//            return result;
//        }
//        if(companyInfo.getCardid()==null) {
//            result.setResultCode(ResultCode.COMPANY_CARID_IS_NULL);
//            return result;
//        }
//        CompanyInfo companyIn = companyService.findById(companyInfo.getId());
//        if(companyIn==null) {
//        	result.setResultCode(ResultCode.COMPANY_IS_NULL);
//            return result;
//        }
//        CompanyInfo company = companyService.update(companyInfo);
//        if(company==null) {
//            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
//        }
//        return result.success(company);
//    }
//    @ApiOperation(value = "获取公司列表信息", notes = "获取公司列表信息", tags = {"COMPANY"})
//    @RequestMapping(value="/getCompanyList", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getCompanyList(
//            @ApiParam(name = "query", value = "查询列表参数", required = true)
//            @RequestBody InfoQuery query) {
//        Result result = new Result();
//        InfoQueryResult<CompanyInfo> queryResult = new InfoQueryResult<CompanyInfo>();
//        int count = companyService.findByAll().length;
//        if (count == 0) {
//            queryResult.setDatas(new CompanyInfo[0]);
//            queryResult.setTotal(count);
//            return result.success(queryResult);
//        }
//        CompanyInfo[] datas = companyService.findByAll();
//        queryResult.setDatas(datas);
//        queryResult.setTotal(count);
//        return result.success(queryResult);
//    }
//}
