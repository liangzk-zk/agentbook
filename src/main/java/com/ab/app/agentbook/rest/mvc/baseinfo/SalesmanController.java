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

import com.ab.app.agentbook.baseinfo.info.SalesmanInfo;
import com.ab.app.agentbook.baseinfo.service.SalesmanService;
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
@RestController("salesmanController")
@RequestMapping("/rest/baseinfo/salesman")
public class SalesmanController implements InitializingBean{
    @Autowired
    @Qualifier(value="salesmanService")
    private SalesmanService salesmanService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(salesmanService, "salesmanService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("fundcode", "fundcode");
            queryUserFieldMapping.put("fundname", "fundname");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取业务员信息", notes = "获取业务员信息", tags = {"SALESMAN"})
    @RequestMapping(value="/getSalesmanList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取业务员信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取业务员信息API内部报错") })
    @ResponseBody
    public Result getSalesmanList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<SalesmanInfo> queryResult = new InfoQueryResult<SalesmanInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = salesmanService.getSalesmanCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new SalesmanInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createdate DESC";
        }
        SalesmanInfo[] datas = salesmanService.getSalesmans(criterions, startPosition,
                maxResults, orderBy);
        List<SalesmanInfo> fundInfos = new ArrayList<SalesmanInfo>();
        for(SalesmanInfo info:datas) {
            fundInfos.add(info);
        }
        queryResult.setDatas(fundInfos.toArray(new SalesmanInfo[fundInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增业务员信息", notes = "新增业务员信息", tags = {"SALESMAN"})
    @RequestMapping(value="/addSalesman", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增业务员信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用业务员信息API内部报错") })
    @ResponseBody
    public Result addSalesman(@RequestBody SalesmanInfo info) {
        Result result = new Result();
        SalesmanInfo parent = salesmanService.findByCode(info.getCode());
        if(parent!=null) {
            result.setResultCode(ResultCode.YWY_HAS_EXISTED);
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.YWY_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.YWY_INFO_NAME_IS_NULL);
            return result;
        }
        SalesmanInfo bean = salesmanService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.WLLB_INFO_SAVE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "更新业务员信息", notes = "更新业务员", tags = {"SALESMAN"})
    @RequestMapping(value="/updateSalesman", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新业务员成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新业务员API内部报错") })
    @ResponseBody
    public Result updateSalesman(@RequestBody SalesmanInfo info) {
        Result result = new Result();
        if(info.getId()==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.YWY_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.YWY_INFO_NAME_IS_NULL);
            return result;
        }
        SalesmanInfo salesmanInfo = salesmanService.findById(info.getId());
        if(salesmanInfo==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        info = salesmanService.update(info);
        if(info==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return result.success(info);
    }
    @ApiOperation(value = "获取业务员信息", notes = "获取业务员信息", tags = {"SALESMAN"})
    @RequestMapping(value="/getSalesman/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getSalesman(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        SalesmanInfo info = salesmanService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return result.success(info);
    }
    @ApiOperation(value = "删除业务员信息", notes = "删除业务员信息", tags = {"SALESMAN"})
    @RequestMapping(value="/deleteSalesman/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteSalesman(
            @ApiParam(name = "id", value = "业务员ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        SalesmanInfo info = salesmanService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        salesmanService.removeSalesman(id);
        return result.success(info);
    }
}
