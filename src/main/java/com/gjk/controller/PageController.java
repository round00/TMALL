package com.gjk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String getLoginPage(){
        return "fore/login";
    }
    @RequestMapping(value = "/registerPage", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "fore/register";
    }
}
