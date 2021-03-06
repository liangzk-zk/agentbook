/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.rest.enums;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年10月30日
*/
public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(200, "成功"),

    /* 往来类别错误：10001-19999 */
    WLLB_INFO_IS_NULL(10001, "往来类别为空！，请重新输入！"),
    WLLB_INFO_CODE_IS_NULL(10002,"往来类别编码为空，请重新输入！"),
    WLLB_INFO_NAME_IS_NULL(10003,"往来类别名称为空，请重新输入！"),
    WLLB_INFO_HAS_EXISTED(10004, "往来类别已存在，请重新输入！"),
    WLLB_INFO_SAVE_SUCCESS(10005, "往来类别保存成功！"),
    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    USER_REFERRER_NOT_EXISTED(20009, "推荐用户不存在"),
    USER_LOGIN_PASSWD_ERROR(20006, "密码错误"),
    USER_REFERRER_NOT_EXIST(20007, "推荐人账户不存在"),
    DELETE_PAYMENT_SUCCESS(20008, "删除上传凭证成功！"),
    USER_REFERRER_IS_NULL(200010, "该用户不存在推荐账户"),
    USER_REFERRERACCOUNT_IS_NULL(200011, "该用户推荐账户为空!"),
    /* 资金账户错误：30001-39999 */
    ZZZH_HAS_EXISTED(30001, "资金账户已存在，请重新输入！"),
    ZZZH_INFO_CODE_IS_NULL(30002, "资金账户编码不能为空，请重新输入！"),
    ZZZH_INFO_NAME_IS_NULL(30003, "资金账户名称不能为空，请重新输入！"),
    ZZZH_INFO_SAVE_SUCCESS(30004, "资金账户保存成功"),
    COIN_NAME_IS_NULL(30005,"请设置数字货币名称"),
    COIN_PRICE_IS_NULL(30006,"请设置数字货币单价"),
    COMPANY_BANKNAME_IS_NULL(30007,"开户行不允许为空"),
    COMPANY_CARID_IS_NULL(30008,"银行卡号不允许为空"),
    COMPANY_IS_NULL(30009,"银行卡号不允许为空"),
    CASH_ALREADY_EXISTED(3000010, "当前您已经有一条正在处理的提现数据，请稍后再试！"),
    CASH_ID_IS_NULL(3000011, "提现数据ID为空"),
    CASH_CARDID_IS_NULL(300012,"提现银行卡号不允许为空"),
    CASH_CARDID_NONE(300013,"提现银行卡信息未找到"),
    CASH_CARDID_SET_DEFAULT(300014,"提现银行卡信息未找到,请设置默认银行卡！"),
    CASH_IS_NOT_AMPLE(300015,"提现金额大于现金点值，请重新输入！"),
    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    USERRECHARGE_DATA_NONE(40002, "当前用户没有额度信息，请上传支付凭证进行额度生成！"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限");
    private Integer code;

    private String message;
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
    public void setMessage(String msg) {
        message = msg;
    }
    @Override
    public String toString() {
        return this.name();
    }

    //校验重复的code值
    public static void main(String[] args) {
        ResultCode[] ApiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<Integer>();
        for (ResultCode ApiResultCode : ApiResultCodes) {
            if (codeList.contains(ApiResultCode.code)) {
                System.out.println(ApiResultCode.code);
            } else {
                codeList.add(ApiResultCode.code());
            }
        }
    }
}
