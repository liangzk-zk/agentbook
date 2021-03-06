/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.mvc.company;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ab.app.agentbook.common.tree.bean.TreeNodeWithChild;
import com.ab.app.agentbook.company.info.AbTransactionInfo;
import com.ab.app.agentbook.company.service.AbTransactionService;
import com.ab.app.agentbook.rest.enums.ResultCode;
import com.ab.app.agentbook.rest.query.Result;
import com.ab.app.agentbook.rest.query.customerInfo.InfoTransactionQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/rest/transaction")
public class TypeTransactionController {
    @Autowired
    @Qualifier(value="abTransactionService")
    private AbTransactionService abTransactionService;
    @ApiOperation(value = "获取往来类别信息", notes = "获取往来类别信息", tags = {"TRANSACTION"})
    @RequestMapping(value="/getTransactionList", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取往来类别信息成功"),
            @ApiResponse(code = 415, message = "请求的参数不合法"),
            @ApiResponse(code = 500, message = "调用获取往来类别信息API内部报错") })
    @ResponseBody
    public TreeNodeWithChild[] getTransactionList(@RequestParam(value="id") long id) {
        AbTransactionInfo parent = abTransactionService.findById(id);
        List<TreeNodeWithChild> result = new ArrayList<TreeNodeWithChild>();
        AbTransactionInfo[] child = abTransactionService.findByParentId(parent.getId());
        if(id==1) {
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
