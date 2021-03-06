package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userRecharagePCController")
@RequestMapping(value="/pcUserRecharage")
public class UserRecharageController {
	@RequestMapping(path = "/page/userRecharageList")
    public String showUserRecharageListPage() {
        return "recharge/rechargeList";
    }
}
