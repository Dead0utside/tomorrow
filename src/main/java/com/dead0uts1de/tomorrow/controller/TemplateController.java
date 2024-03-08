package com.dead0uts1de.tomorrow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("register")
    public String getRegister() {
        return "register";
    }

//    @GetMapping("index")
//    public String getIndex() {
//        return "index";
//    }

    @GetMapping("home")
    public String getHome() {
        return "home";
    }
}
