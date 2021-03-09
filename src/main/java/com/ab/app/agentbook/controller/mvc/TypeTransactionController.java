package com.ab.app.agentbook.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("typeTransactionPCController")
@RequestMapping(value="/pcTypeTransaction")
public class TypeTransactionController {
	@RequestMapping(path = "/page/typeTransactionList")
    public String showTypeTransactionListListPage() {
        return "customerInfo/typeTransactionList";
    }
}
