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

import com.ab.app.agentbook.baseinfo.info.ReceivePayInfo;
import com.ab.app.agentbook.baseinfo.service.ReceivePayService;
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
@RestController("receivePayController")
@RequestMapping("/rest/baseinfo/receivePay")
public class ReceivePayController implements InitializingBean{
    @Autowired
    @Qualifier(value="receivePayService")
    private ReceivePayService receivePayService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(receivePayService, "receivePayService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("fundcode", "fundcode");
            queryUserFieldMapping.put("fundname", "fundname");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取收款项目信息", notes = "获取收款项目信息", tags = {"RECEIVEPAY"})
    @RequestMapping(value="/getReceivePayList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取收款项目信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取收款项目信息API内部报错") })
    @ResponseBody
    public Result getReceivePayList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<ReceivePayInfo> queryResult = new InfoQueryResult<ReceivePayInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = receivePayService.getReceivePayCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new ReceivePayInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createdate DESC";
        }
        ReceivePayInfo[] datas = receivePayService.getReceivePays(criterions, startPosition,
                maxResults, orderBy);
        List<ReceivePayInfo> fundInfos = new ArrayList<ReceivePayInfo>();
        for(ReceivePayInfo info:datas) {
            fundInfos.add(info);
        }
        queryResult.setDatas(fundInfos.toArray(new ReceivePayInfo[fundInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增收款项目信息", notes = "新增收款项目信息", tags = {"RECEIVEPAY"})
    @RequestMapping(value="/addReceivePay", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增收款项目信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用收款项目信息API内部报错") })
    @ResponseBody
    public Result addReceivePay(@RequestBody ReceivePayInfo info) {
        Result result = new Result();
        ReceivePayInfo parent = receivePayService.findByCode(info.getCode());
        if(parent!=null) {
            result.setResultCode(ResultCode.SKXM_HAS_EXISTED);
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.SKXM_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.SKXM_INFO_NAME_IS_NULL);
            return result;
        }
        ReceivePayInfo bean = receivePayService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.SKXM_INFO_SAVE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "更新收款项目信息", notes = "更新收款项目1", tags = {"RECEIVEPAY"})
    @RequestMapping(value="/updateReceivePay", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新收款项目成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新收款项目API内部报错") })
    @ResponseBody
    public Result updateReceivePay(@RequestBody ReceivePayInfo info) {
        Result result = new Result();
        if(info.getId()==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.SKXM_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.SKXM_INFO_NAME_IS_NULL);
            return result;
        }
        ReceivePayInfo receivePayInfo = receivePayService.findById(info.getId());
        if(receivePayInfo==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        info = receivePayService.update(info);
        if(info==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return result.success(info);
    }
    @ApiOperation(value = "获取收款项目信息", notes = "获取收款项目信息", tags = {"RECEIVEPAY"})
    @RequestMapping(value="/getReceivePay/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getReceivePay(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        ReceivePayInfo info = receivePayService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return result.success(info);
    }
    @ApiOperation(value = "删除收款项目信息", notes = "删除收款项目信息", tags = {"RECEIVEPAY"})
    @RequestMapping(value="/deleteReceivePay/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteReceivePay(
            @ApiParam(name = "id", value = "收款项目ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        ReceivePayInfo info = receivePayService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        receivePayService.removeReceivePay(id);
        return result.success(info);
    }
}
