package com.wimdeblauwe.examples.spendleaf.approval.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/approvals")
class ApprovalController {
    @GetMapping
    public String index() {
        return "approvals/index";
    }
}
