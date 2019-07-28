package com.haidarvm.indekskepuasan.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("title", "Dashboard");
        logger.debug("Created file xlx {}", "mana ini excel nya siiiih");
        return "home";
    }
}
