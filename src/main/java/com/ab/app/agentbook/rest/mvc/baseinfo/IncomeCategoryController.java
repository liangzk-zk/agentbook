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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.baseinfo.info.IncomeCategoryInfo;
import com.ab.app.agentbook.baseinfo.service.IncomeCategoryService;
import com.ab.app.agentbook.common.tree.bean.TreeNodeWithChild;
import com.ab.app.agentbook.data.crud.criteria.Criterion;
import com.ab.app.agentbook.jpa.ws.Expression;
import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.InfoQuery;
import com.ab.app.agentbook.rest.query.InfoQueryResult;
import com.ab.app.agentbook.rest.query.Result;
import com.ab.app.agentbook.security.user.info.User;
import com.ab.app.agentbook.security.user.info.UserInfo;
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
@RestController("incomeCategoryController")
@RequestMapping("/rest/baseinfo/incomeCategory")
public class IncomeCategoryController implements InitializingBean{
    @Autowired
    @Qualifier(value="incomeCategoryService")
    private IncomeCategoryService incomeCategoryService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(incomeCategoryService, "incomeCategoryService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("fundcode", "fundcode");
            queryUserFieldMapping.put("fundname", "fundname");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取收款项目类型信息", notes = "获取收款项目类型信息", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/getIncomeCategoryTreeList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取收款项目类型信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取收款项目类型信息API内部报错") })
    @ResponseBody
    public TreeNodeWithChild[] getIncomeCategoryTreeList(@RequestParam(value="id") long id,@RequestParam(value="isRoot",required = false) boolean isRoot) {

        IncomeCategoryInfo parent = incomeCategoryService.findById(id);
        List<TreeNodeWithChild> result = new ArrayList<TreeNodeWithChild>();
        IncomeCategoryInfo[] child = incomeCategoryService.findByParentId(parent.getId());
        if(isRoot) {
            TreeNodeWithChild info = new TreeNodeWithChild();
            info.setId(String.valueOf(parent.getId()));
            info.setName(parent.getName());
            info.setIsParent(true);
            info.setLeaf(false);
            List<TreeNodeWithChild> childInfo = new ArrayList<TreeNodeWithChild>();
            for(IncomeCategoryInfo c:child) {
                childInfo.add(warp(c,parent.getId()));
            }
            info.setChildren(childInfo.toArray(new TreeNodeWithChild[childInfo.size()]));
            result.add(info);
        }else {
            for(IncomeCategoryInfo c:child) {
                result.add(warp(c,parent.getId()));
            }
        }
        return result.toArray(new TreeNodeWithChild[result.size()]);
    }
    
    @ApiOperation(value = "获取收款项目类型列表信息", notes = "获取收款项目类型列表信息", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/getIncomeCategoryList", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Result getIncomeCategoryList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<IncomeCategoryInfo> queryResult = new InfoQueryResult<IncomeCategoryInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = incomeCategoryService.getIncomeCategoryCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new IncomeCategoryInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createdate DESC";
        }
        IncomeCategoryInfo[] datas = incomeCategoryService.getIncomeCategorys(criterions, startPosition,
                maxResults, orderBy);
        queryResult.setDatas(datas);
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增收款项目类别信息", notes = "新增收款项目类别信息", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/addIncomeCategory", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增收款项目类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用收款项目类别信息API内部报错") })
    @ResponseBody
    public Result addIncomeCategory(@RequestBody IncomeCategoryInfo info) {
        Result result = new Result();
        IncomeCategoryInfo parent = incomeCategoryService.findByCode(info.getCode());
        if(parent!=null) {
            result.setResultCode(ResultCode.SKXMLB_HAS_EXISTED);
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.SKXMLB_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.SKXMLB_INFO_NAME_IS_NULL);
            return result;
        }
        IncomeCategoryInfo bean = incomeCategoryService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.SKXMLB_INFO_SAVE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "更新收款项目类别信息", notes = "更新收款项目类别", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/updateIncomeCategory", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新收款项目类别成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新收款项目类别API内部报错") })
    @ResponseBody
    public Result updateIncomeCategory(@RequestBody IncomeCategoryInfo info) {
        Result result = new Result();
        if(info.getId()==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.SKXMLB_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.SKXMLB_INFO_NAME_IS_NULL);
            return result;
        }
        IncomeCategoryInfo incomeCategory = incomeCategoryService.findById(info.getId());
        if(incomeCategory==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        info = incomeCategoryService.update(info);
        if(info==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        result.setData(info);
        result.setResultCode(ResultCode.SKXMLB_INFO_UPDATE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "获取收款项目类别信息", notes = "获取收款项目类别信息", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/getIncomeCategory/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getIncomeCategory(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        IncomeCategoryInfo info = incomeCategoryService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return result.success(info);
    }
    @ApiOperation(value = "删除收款项目类别信息", notes = "删除收款项目类别信息", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/deleteIncomeCategory/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteIncomeCategory(
            @ApiParam(name = "id", value = "收款项目类别ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        IncomeCategoryInfo info = incomeCategoryService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        incomeCategoryService.removeIncomeCategory(id);
        return result.success(info);
    }
    private TreeNodeWithChild warp(IncomeCategoryInfo c,long pid) {
        if(c==null) {
            return new TreeNodeWithChild();
        }
        TreeNodeWithChild info = new TreeNodeWithChild();
        info.setId(String.valueOf(c.getId()));
        info.setName(c.getName());
        IncomeCategoryInfo[] child = incomeCategoryService.findByParentId(c.getId());
        if(child.length>0) {
            info.setIsParent(true); 
        }
        info.setLeaf(true);
        info.setpId(String.valueOf(pid));
        return info;
    }
}
