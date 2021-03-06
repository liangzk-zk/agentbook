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

import com.ab.app.agentbook.baseinfo.info.PaymentCategoryInfo;
import com.ab.app.agentbook.baseinfo.service.PaymentCategoryService;
import com.ab.app.agentbook.common.tree.bean.TreeNodeWithChild;
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
@RestController("paymentCategoryController")
@RequestMapping("/rest/baseinfo/paymentCategory")
public class PaymentCategoryController implements InitializingBean{
    @Autowired
    @Qualifier(value="paymentCategoryService")
    private PaymentCategoryService paymentCategoryService;
    private Map<String, String> queryUserFieldMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(paymentCategoryService, "paymentCategoryService is required!");
        if(queryUserFieldMapping == null) {
            queryUserFieldMapping = new HashMap<String, String>(); 
            queryUserFieldMapping.put("fundcode", "fundcode");
            queryUserFieldMapping.put("fundname", "fundname");
            queryUserFieldMapping.put("selfcode", "selfcode");
        }
    }
    @ApiOperation(value = "获取付款项目类别信息", notes = "获取付款项目类别信息", tags = {"INCONMECATEGORY"})
    @RequestMapping(value="/getPaymentCategoryTreeList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取付款项目类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取付款项目类别信息API内部报错") })
    @ResponseBody
    public TreeNodeWithChild[] getPaymentCategoryTreeList(@RequestParam(value="id") long id,@RequestParam(value="isRoot",required = false) boolean isRoot) {

        PaymentCategoryInfo parent = paymentCategoryService.findById(id);
        List<TreeNodeWithChild> result = new ArrayList<TreeNodeWithChild>();
        PaymentCategoryInfo[] child = paymentCategoryService.findByParentId(parent.getId());
        if(isRoot) {
            TreeNodeWithChild info = new TreeNodeWithChild();
            info.setId(String.valueOf(parent.getId()));
            info.setName(parent.getName());
            info.setIsParent(true);
            info.setLeaf(false);
            List<TreeNodeWithChild> childInfo = new ArrayList<TreeNodeWithChild>();
            for(PaymentCategoryInfo c:child) {
                childInfo.add(warp(c,parent.getId()));
            }
            info.setChildren(childInfo.toArray(new TreeNodeWithChild[childInfo.size()]));
            result.add(info);
        }else {
            for(PaymentCategoryInfo c:child) {
                result.add(warp(c,parent.getId()));
            }
        }
        return result.toArray(new TreeNodeWithChild[result.size()]);
    }
    @ApiOperation(value = "获取付款项目类别信息", notes = "获取付款项目类别信息", tags = {"PAYMENTCATEGORY"})
    @RequestMapping(value="/getPaymentCategoryList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取付款项目类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取付款项目类别信息API内部报错") })
    @ResponseBody
    public Result getPaymentCategoryList(
            @ApiParam(name = "query", value = "查询列表参数", required = true)
            @RequestBody InfoQuery query) {
        Result result = new Result();
        InfoQueryResult<PaymentCategoryInfo> queryResult = new InfoQueryResult<PaymentCategoryInfo>();
        Expression[] expressions = query.getExpressions();
        String orderBy = query.getOrderBy();
        int startPosition = query.getStartPosition();
        int maxResults = query.getMaxResults();
        Criterion[] criterions = CriterionUtil.toCriterions(expressions,queryUserFieldMapping);
        int count = paymentCategoryService.getPaymentCategoryCount(criterions);
        if (count == 0) {
            queryResult.setDatas(new PaymentCategoryInfo[0]);
            queryResult.setTotal(count);
            return result.success(queryResult);
        }
        if(StringUtils.isEmpty(orderBy)) {
            orderBy = "createdate DESC";
        }
        PaymentCategoryInfo[] datas = paymentCategoryService.getPaymentCategorys(criterions, startPosition,
                maxResults, orderBy);
        List<PaymentCategoryInfo> fundInfos = new ArrayList<PaymentCategoryInfo>();
        for(PaymentCategoryInfo info:datas) {
            fundInfos.add(info);
        }
        queryResult.setDatas(fundInfos.toArray(new PaymentCategoryInfo[fundInfos.size()]));
        queryResult.setTotal(count);
        return result.success(queryResult);
    }
    @ApiOperation(value = "新增付款项目类别信息", notes = "新增付款项目类别信息", tags = {"PAYMENTCATEGORY"})
    @RequestMapping(value="/addPaymentCategory", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "新增付款项目类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用付款项目类别信息API内部报错") })
    @ResponseBody
    public Result addPaymentCategory(@RequestBody PaymentCategoryInfo info) {
        Result result = new Result();
        PaymentCategoryInfo parent = paymentCategoryService.findByCode(info.getCode());
        if(parent!=null) {
            result.setResultCode(ResultCode.FKXMLB_HAS_EXISTED);
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.FKXMLB_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.FKXMLB_INFO_NAME_IS_NULL);
            return result;
        }
        PaymentCategoryInfo bean = paymentCategoryService.save(info);
        result.setData(bean);
        result.setResultCode(ResultCode.WLLB_INFO_SAVE_SUCCESS);
        return result;
    }
    @ApiOperation(value = "更新付款项目类别信息", notes = "更新付款项目类别", tags = {"PAYMENTCATEGORY"})
    @RequestMapping(value="/updatePaymentCategory", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新付款项目类别成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用更新付款项目类别API内部报错") })
    @ResponseBody
    public Result updatePaymentCategory(@RequestBody PaymentCategoryInfo info) {
        Result result = new Result();
        if(info.getId()==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        if(StringUtils.isBlank(info.getCode())) {
            result.setResultCode(ResultCode.FKXMLB_INFO_CODE_IS_NULL);
            return result;
        }
        if(StringUtils.isBlank(info.getName())) {
            result.setResultCode(ResultCode.FKXMLB_INFO_NAME_IS_NULL);
            return result;
        }
        PaymentCategoryInfo paymentCategoryInfo = paymentCategoryService.findById(info.getId());
        if(paymentCategoryInfo==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        info = paymentCategoryService.update(info);
        if(info==null) {
            return result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return result.success(info);
    }
    @ApiOperation(value = "获取付款项目类别信息", notes = "获取付款项目类别信息", tags = {"PAYMENTCATEGORY"})
    @RequestMapping(value="/getPaymentCategory/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getPaymentCategory(
            @ApiParam(name = "id", value = "用户ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        PaymentCategoryInfo info = paymentCategoryService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return result.success(info);
    }
    @ApiOperation(value = "删除付款项目类别信息", notes = "删除付款项目类别信息", tags = {"PAYMENTCATEGORY"})
    @RequestMapping(value="/deletePaymentCategory/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deletePaymentCategory(
            @ApiParam(name = "id", value = "付款项目类别ID",example = "1", required = true) @PathVariable Long id) {
        Result result = new Result();
        if(id==null) {
            result.setCode(10002);
            result.setMsg("id不允许为空！");
            return result;
        }
        PaymentCategoryInfo info = paymentCategoryService.findById(id);
        if(info==null) {
            return result.failure(ResultCode.RESULE_DATA_NONE);
        }
        paymentCategoryService.removePaymentCategory(id);
        return result.success(info);
    }
    private TreeNodeWithChild warp(PaymentCategoryInfo c,long pid) {
        if(c==null) {
            return new TreeNodeWithChild();
        }
        TreeNodeWithChild info = new TreeNodeWithChild();
        info.setId(String.valueOf(c.getId()));
        info.setName(c.getName());
        PaymentCategoryInfo[] child = paymentCategoryService.findByParentId(c.getId());
        if(child.length>0) {
            info.setIsParent(true); 
        }
        info.setLeaf(true);
        info.setpId(String.valueOf(pid));
        return info;
    }
}
