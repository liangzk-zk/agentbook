/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.mvc.baseinfo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.baseinfo.info.PaymentInfo;
import com.ab.app.agentbook.baseinfo.service.PaymentService;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.jpa.ws.Expression;
import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.InfoQuery;
import com.ab.app.agentbook.rest.query.InfoQueryResult;
import com.ab.app.agentbook.rest.query.Result;
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
* @version 1.0, 2020年10月30日
*/
@Api
@RestController("paymentController")
@RequestMapping("/rest/baseinfo/payment")
public class PaymentController implements InitializingBean{
    @Autowired
    @Qualifier(value="paymentService")
    private PaymentService paymentService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(paymentService, "paymentService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取付款项目信息", notes = "获取付款项目信息", tags = {"PAYMENT"})
    @RequestMapping(value="/getPaymentList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取付款项目信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取付款项目信息API内部报错") })
    @ResponseBody
    public Result getPaymentList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<PaymentInfo> queryResult = new InfoQueryResult<PaymentInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = paymentService.getPaymentCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new PaymentInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createdate DESC";
        }
        PaymentInfo[] datas = paymentService.getPayments(criterions, startPosition,
                maxResults, orderBy);
        List<PaymentInfo> fundInfos = new ArrayList<PaymentInfo>();
        for(PaymentInfo info:datas) {
            fundInfos.add(info);
        }
        queryResult.setDatas(fundInfos.toArray(new PaymentInfo[fundInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增付款项目信息", notes = "新增付款项目信息", tags = {"PAYMENT"})
    @RequestMapping(value="/addPayment", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增付款项目信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用付款项目信息API内部报错") })
    @ResponseBody
    public Result addPayment(@RequestBody PaymentInfo info) {
        Result result = new Result();
        PaymentInfo parent = paymentService.findByCode(info.getCode());
        if(parent!=null) {
            result.setResultCode(ResultCode.FKXM_HAS_EXISTED);
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.FKXM_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.FKXM_INFO_NAME_IS_NULL);
            return result;
        }
        PaymentInfo bean = paymentService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.FKXM_INFO_SAVE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "更新付款项目信息", notes = "更新付款项目", tags = {"PAYMENT"})
    @RequestMapping(value="/updatePayment", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新付款项目成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新付款项目API内部报错") })
    @ResponseBody
    public Result updatePayment(@RequestBody PaymentInfo info) {
        Result result = new Result();
        if(info.getId()==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.FKXM_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.FKXM_INFO_NAME_IS_NULL);
            return result;
        }
        PaymentInfo paymentInfo = paymentService.findById(info.getId());
        if(paymentInfo==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        info = paymentService.update(info);
        if(info==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        result.setData(info);
        result.setResultCode(ResultCode.FKXM_INFO_UPDATE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "获取付款项目信息", notes = "获取付款项目信息", tags = {"PAYMENT"})
    @RequestMapping(value="/getPayment/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getPayment(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        PaymentInfo info = paymentService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return result.success(info);
    }
    @ApiOperation(value = "删除付款项目信息", notes = "删除付款项目信息", tags = {"PAYMENT"})
    @RequestMapping(value="/deletePayment/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deletePayment(
            @ApiParam(name = "id", value = "付款项目ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        PaymentInfo info = paymentService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        paymentService.removePayment(id);
        return result.success(info);
    }
}
