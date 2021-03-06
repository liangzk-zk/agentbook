/*
* Copyright 2013-2021 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2021年3月5日
*/
@Controller("paymentPCController")
@RequestMapping(value="/pcPayment")
public class PaymentController {
    @RequestMapping(path = "/page/paymentList")
    public String showPaymentPage() {
        return "baseinfo/paymentList";
    }
}
