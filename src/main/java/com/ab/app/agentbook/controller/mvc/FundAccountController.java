package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("fundAccountPCController")
@RequestMapping(value="/pcFundAccount")
public class FundAccountController {
	@RequestMapping(path = "/page/fundAccountList")
    public String showListPage() {
        return "baseinfo/fundAccountList";
    }
}
