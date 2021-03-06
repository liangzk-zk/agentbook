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

import com.ab.app.agentbook.baseinfo.info.FundAccountInfo;
import com.ab.app.agentbook.baseinfo.service.FundAccountService;
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
@RestController("fundAccountController")
@RequestMapping("/rest/baseinfo/fundAccount")
public class FundAccountController implements InitializingBean{
    @Autowired
    @Qualifier(value="fundAccountService")
    private FundAccountService fundAccountService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(fundAccountService, "fundAccountService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("fundcode", "fundcode");
            queryUserFieldMapping.put("fundname", "fundname");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取资金账户信息", notes = "获取资金账户信息", tags = {"FUNDACCOUNT"})
    @RequestMapping(value="/getFundAccountList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取资金账户信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取资金账户信息API内部报错") })
    @ResponseBody
    public Result getFundAccountList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<FundAccountInfo> queryResult = new InfoQueryResult<FundAccountInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = fundAccountService.getFundAccountCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new FundAccountInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createTime DESC";
        }
        FundAccountInfo[] datas = fundAccountService.getFundAccounts(criterions, startPosition,
                maxResults, orderBy);
        List<FundAccountInfo> fundInfos = new ArrayList<FundAccountInfo>();
        for(FundAccountInfo info:datas) {
            fundInfos.add(info);
        }
        queryResult.setDatas(fundInfos.toArray(new FundAccountInfo[fundInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增资金账户信息", notes = "新增资金账户信息", tags = {"FUNDACCOUNT"})
    @RequestMapping(value="/addFundAccount", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增资金账户信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用资金账户信息API内部报错") })
    @ResponseBody
    public Result addFundAccount(@RequestBody FundAccountInfo info) {
        Result result = new Result();
        FundAccountInfo parent = fundAccountService.findByFundCode(info.getFundcode());
        if(parent!=null) {
            result.setResultCode(ResultCode.ZZZH_HAS_EXISTED);
            return result;
        }
        if(StringUtils.isBlank(info.getFundcode())) {
            result.setResultCode(ResultCode.ZZZH_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getFundname())) {
            result.setResultCode(ResultCode.ZZZH_INFO_NAME_IS_NULL);
            return result;
        }
        FundAccountInfo bean = fundAccountService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.ZZZH_INFO_SAVE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "更新资金账户信息", notes = "更新资金账户", tags = {"FUNDACCOUNT"})
    @RequestMapping(value="/updateFundAccount", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新资金账户成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新资金账户API内部报错") })
    @ResponseBody
    public Result updateUser(@RequestBody FundAccountInfo info) {
        Result result = new Result();
        if(info.getId()==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        if(StringUtils.isBlank(info.getFundcode())) {
            result.setResultCode(ResultCode.ZZZH_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getFundname())) {
            result.setResultCode(ResultCode.ZZZH_INFO_NAME_IS_NULL);
            return result;
        }
        FundAccountInfo fundAccount = fundAccountService.findById(info.getId());
        if(fundAccount==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        info = fundAccountService.update(info);
        if(info==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return result.success(info);
    }
    @ApiOperation(value = "获取资金账户信息", notes = "获取资金账户信息", tags = {"FUNDACCOUNT"})
    @RequestMapping(value="/getFundAccount/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getUser(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        FundAccountInfo info = fundAccountService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return result.success(info);
    }
    @ApiOperation(value = "删除资金账户信息", notes = "删除资金账户信息", tags = {"FUNDACCOUNT"})
    @RequestMapping(value="/deleteFundAccount/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteUser(
            @ApiParam(name = "id", value = "资金账户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        FundAccountInfo info = fundAccountService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        fundAccountService.removeFundAccount(id);
        return result.success(info);
    }
}
