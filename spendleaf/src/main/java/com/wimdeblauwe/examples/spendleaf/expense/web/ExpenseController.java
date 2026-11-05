package com.wimdeblauwe.examples.spendleaf.expense.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expenses")
class ExpenseController {
    @GetMapping
    public String index() {
        return "expenses/index";
    }
}
