package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("incomeCategoryPCController")
@RequestMapping(value="/pcIncomeCategory")
public class IncomeCategoryController {
	@RequestMapping(path = "/page/incomeCategoryList")
    public String showIncomeCategoryListPage() {
        return "baseinfo/incomeCategoryList";
    }
}
