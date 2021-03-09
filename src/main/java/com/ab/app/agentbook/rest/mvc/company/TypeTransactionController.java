/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.mvc.company;



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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.baseinfo.info.IncomeCategoryInfo;
import com.ab.app.agentbook.common.tree.bean.TreeNodeWithChild;
import com.ab.app.agentbook.company.info.AbTransactionInfo;
import com.ab.app.agentbook.company.service.AbTransactionService;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.jpa.ws.Expression;
import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.InfoQuery;
import com.ab.app.agentbook.rest.query.InfoQueryResult;
import com.ab.app.agentbook.rest.query.Result;
import com.ab.app.agentbook.rest.query.customerInfo.InfoTransactionQuery;
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
@RestController("transactionController")
@RequestMapping("/rest/customerInfo/typeTransaction")
public class TypeTransactionController implements InitializingBean{
    @Autowired
    @Qualifier(value="abTransactionService")
    private AbTransactionService abTransactionService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(abTransactionService, "incomeCategoryService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("code", "code");
            queryUserFieldMapping.put("name", "name");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取往来类别信息", notes = "获取往来类别信息", tags = {"TRANSACTION"})
    @RequestMapping(value="/getTransactionTreeList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取往来类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取往来类别信息API内部报错") })
    @ResponseBody
    public TreeNodeWithChild[] getTransactionTreeList(@RequestParam(value="id") long id,
            @RequestParam(value="isRoot",required = false) boolean isRoot) {
        AbTransactionInfo parent = abTransactionService.findById(id);
        List<TreeNodeWithChild> result = new ArrayList<TreeNodeWithChild>();
        AbTransactionInfo[] child = abTransactionService.findByParentId(parent.getId());
        if(isRoot) {
            TreeNodeWithChild info = new TreeNodeWithChild();
            info.setId(String.valueOf(parent.getId()));
            info.setName(parent.getName());
            info.setIsParent(true);
            info.setLeaf(false);
            List<TreeNodeWithChild> childInfo = new ArrayList<TreeNodeWithChild>();
            for(AbTransactionInfo c:child) {
                childInfo.add(warp(c,parent.getId()));
            }
            info.setChildren(childInfo.toArray(new TreeNodeWithChild[childInfo.size()]));
            result.add(info); 
        }else {
            for(AbTransactionInfo c:child) {
                result.add(warp(c,parent.getId()));
            }
        }
        return result.toArray(new TreeNodeWithChild[result.size()]);
    }
    @ApiOperation(value = "获取往来类别列表信息", notes = "获取往来类别列表信息", tags = {"TRANSACTION"})
    @RequestMapping(value="/getTransactionList", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Result getTransactionList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<AbTransactionInfo> queryResult = new InfoQueryResult<AbTransactionInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = abTransactionService.getIncomeCategoryCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new AbTransactionInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createdate DESC";
        }
        AbTransactionInfo[] datas = abTransactionService.getIncomeCategorys(criterions, startPosition,
                maxResults, orderBy);
        queryResult.setDatas(datas);
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增往来类别信息", notes = "新增往来类别信息", tags = {"TRANSACTION"})
    @RequestMapping(value="/addTransactionInfo", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增往来类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用新增往来类别信息API内部报错") })
    @ResponseBody
    public Result addTransactionInfo(@RequestBody AbTransactionInfo info) {
        Result result = new Result();
        AbTransactionInfo parent = abTransactionService.findById(info.getParentId());
        if(parent==null) {
            result.setResultCode(ResultCode.WLLB_INFO_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.WLLB_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.WLLB_INFO_NAME_IS_NULL);
            return result;
        }
        AbTransactionInfo infoBean = abTransactionService.findByCode(info.getCode());
        if(infoBean!=null) {
            result.setResultCode(ResultCode.WLLB_INFO_HAS_EXISTED);
            return result;
        }
        AbTransactionInfo bean = abTransactionService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.WLLB_INFO_SAVE_SUCCESS);
        return result;
    }
    private TreeNodeWithChild warp(AbTransactionInfo c,long pid) {
        if(c==null) {
            return new TreeNodeWithChild();
        }
        TreeNodeWithChild info = new TreeNodeWithChild();
        info.setId(String.valueOf(c.getId()));
        info.setName(c.getName());
        AbTransactionInfo[] child = abTransactionService.findByParentId(c.getId());
        if(child.length>0) {
            info.setIsParent(true); 
        }
        info.setLeaf(true);
        info.setpId(String.valueOf(pid));
        return info;
    }
}
