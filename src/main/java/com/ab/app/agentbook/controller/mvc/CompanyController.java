package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("companyPCController")
@RequestMapping(value="/pcCompany")
public class CompanyController {
	@RequestMapping(path = "/page/companyList")
    public String showCompanyListPage() {
        return "company/companyList";
    }
}
