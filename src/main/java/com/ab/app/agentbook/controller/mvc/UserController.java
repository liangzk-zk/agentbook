package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userPCController")
@RequestMapping(value="/pcUser")
public class UserController {
	@RequestMapping(path = "/page/userList")
    public String showListPage() {
        return "user/userList";
    }
	@RequestMapping(path = "/page/userSubList")
    public String showSubListPage() {
        return "user/userSubList";
    }
}
