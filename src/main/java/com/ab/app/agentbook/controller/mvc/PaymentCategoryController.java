package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("paymentCategoryPCController")
@RequestMapping(value="/pcPaymentCategory")
public class PaymentCategoryController {
	@RequestMapping(path = "/page/paymentCategoryList")
    public String showPaymentCategoryPage() {
        return "baseinfo/paymentCategoryList";
    }
}
