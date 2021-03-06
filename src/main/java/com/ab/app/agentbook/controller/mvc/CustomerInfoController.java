package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("customerInfoPCController")
@RequestMapping(value="/pcCustomerInfo")
public class CustomerInfoController {
	@RequestMapping(path = "/page/customerInfoList")
    public String showCompanyListPage() {
        return "company/customerInfoList";
    }
}
