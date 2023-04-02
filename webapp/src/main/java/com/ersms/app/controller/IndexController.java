package com.ersms.app.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("attribute", "value");
        return "index";
    }

}
